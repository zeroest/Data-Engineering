package me.zeroest.yarn.example;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.yarn.api.ApplicationConstants;
import org.apache.hadoop.yarn.api.ApplicationConstants.Environment;
import org.apache.hadoop.yarn.api.protocolrecords.GetNewApplicationResponse;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ApplicationReport;
import org.apache.hadoop.yarn.api.records.ApplicationSubmissionContext;
import org.apache.hadoop.yarn.api.records.ContainerLaunchContext;
import org.apache.hadoop.yarn.api.records.FinalApplicationStatus;
import org.apache.hadoop.yarn.api.records.LocalResource;
import org.apache.hadoop.yarn.api.records.LocalResourceType;
import org.apache.hadoop.yarn.api.records.LocalResourceVisibility;
import org.apache.hadoop.yarn.api.records.Priority;
import org.apache.hadoop.yarn.api.records.Resource;
import org.apache.hadoop.yarn.api.records.URL;
import org.apache.hadoop.yarn.api.records.YarnApplicationState;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.client.api.YarnClientApplication;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.apache.hadoop.yarn.util.Records;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class MyClient {

    // Start time for client
    private final long clientStartTime = System.currentTimeMillis();

    // Configuration
    private Configuration conf;

    private YarnClient yarnClient;

    // Application master specific info to register a new Application with RM/ASM
    private String appName = "";

    // App master priority
    private int amPriority = 0;

    // Queue for App master
    private String amQueue = "";

    // Amt. of memory resource to request for to run the App Master
    private long amMemory = 10;

    // Amt. of virtual core resource to request for to run the App Master
    private int amVCores = 1;

    // ApplicationMaster jar file
    private String appMasterJarPath = "";

    // Container priority
    private int requestPriority = 0;

    // Amt of memory to request for container in which the HelloYarn will be executed
    private int containerMemory = 10;

    // Amt. of virtual cores to request for container in which the HelloYarn will be executed
    private int containerVirtualCores = 1;

    // No. of containers in which the HelloYarn needs to be executed
    private int numContainers = 1;

    // Timeout threshold for client. Kill app after time interval expires.
    private long clientTimeout = 600000;

    // Command line options
    private Options opts;

    /**
     * Constructor
     */
    public MyClient() {
        createYarnClient();
        initOptions();
    }

    private void createYarnClient() {
        yarnClient = YarnClient.createYarnClient();
        this.conf = new YarnConfiguration();
        yarnClient.init(conf);
    }

    private void initOptions() {
        opts = new Options();
        opts.addOption("appname", true, "Application Name. Default value - HelloYarn");
        opts.addOption("priority", true, "Application Priority. Default 0");
        opts.addOption("queue", true, "RM Queue in which this application is to be submitted");
        opts.addOption("timeout", true, "Application timeout in milliseconds");
        opts.addOption("master_memory", true,
            "Amount of memory in MB to be requested to run the application master");
        opts.addOption("master_vcores", true,
            "Amount of virtual cores to be requested to run the application master");
        opts.addOption("jar", true, "Jar file containing the application master");
        opts.addOption("container_memory", true, "Amount of memory in MB to be requested to run the HelloYarn");
        opts.addOption("container_vcores", true,
            "Amount of virtual cores to be requested to run the HelloYarn");
        opts.addOption("num_containers", true, "No. of containers on which the HelloYarn needs to be executed");
        opts.addOption("help", false, "Print usage");
    }

    /**
     * Helper function to print out usage
     */
    private void printUsage() {
        new HelpFormatter().printHelp("Client", opts);
    }

    /**
     * Parse command line options
     *
     * @param args Parsed command line options
     * @return Whether the init was successful to run the client
     * @throws org.apache.commons.cli.ParseException
     */
    public boolean init(String[] args) throws ParseException {

        CommandLine cliParser = new GnuParser().parse(opts, args);

        if (args.length == 0) {
            throw new IllegalArgumentException("No args specified for client to initialize");
        }

        if (cliParser.hasOption("help")) {
            printUsage();
            return false;
        }

        appName = cliParser.getOptionValue("appname", "HelloYarn");
        amPriority = Integer.parseInt(cliParser.getOptionValue("priority", "0"));
        amQueue = cliParser.getOptionValue("queue", "default");
        amMemory = Integer.parseInt(cliParser.getOptionValue("master_memory", "10"));
        amVCores = Integer.parseInt(cliParser.getOptionValue("master_vcores", "1"));

        if (amMemory < 0) {
            throw new IllegalArgumentException("Invalid memory specified for application master, exiting."
                + " Specified memory=" + amMemory);
        }
        if (amVCores < 0) {
            throw new IllegalArgumentException(
                "Invalid virtual cores specified for application master, exiting."
                    + " Specified virtual cores=" + amVCores);
        }

        if (!cliParser.hasOption("jar")) {
            throw new IllegalArgumentException("No jar file specified for application master");
        }

        appMasterJarPath = cliParser.getOptionValue("jar");

        containerMemory = Integer.parseInt(cliParser.getOptionValue("container_memory", "10"));
        containerVirtualCores = Integer.parseInt(cliParser.getOptionValue("container_vcores", "1"));
        numContainers = Integer.parseInt(cliParser.getOptionValue("num_containers", "1"));

        if (containerMemory < 0 || containerVirtualCores < 0 || numContainers < 1) {
            throw new IllegalArgumentException("Invalid no. of containers or container memory/vcores specified,"
                + " exiting."
                + " Specified containerMemory=" + containerMemory
                + ", containerVirtualCores=" + containerVirtualCores
                + ", numContainer=" + numContainers);
        }

        clientTimeout = Integer.parseInt(cliParser.getOptionValue("timeout", "600000"));

        return true;
    }

    /**
     * Main run function for the client
     *
     * @return true if application completed successfully
     * @throws java.io.IOException
     * @throws org.apache.hadoop.yarn.exceptions.YarnException
     */
    public boolean run() throws IOException, YarnException {

        System.out.println("Running Client");
        yarnClient.start();

        // Get a new application id
        YarnClientApplication app = yarnClient.createApplication();
        GetNewApplicationResponse appResponse = app.getNewApplicationResponse();

        long maxMem = appResponse.getMaximumResourceCapability().getMemorySize();
        System.out.println("Max mem capabililty of resources in this cluster " + maxMem);

        // A resource ask cannot exceed the max.
        if (amMemory > maxMem) {
            System.out.println("AM memory specified above max threshold of cluster. Using max value."
                + ", specified=" + amMemory
                + ", max=" + maxMem);
            amMemory = maxMem;
        }

        int maxVCores = appResponse.getMaximumResourceCapability().getVirtualCores();
        System.out.println("Max virtual cores capabililty of resources in this cluster " + maxVCores);

        if (amVCores > maxVCores) {
            System.out.println("AM virtual cores specified above max threshold of cluster. "
                + "Using max value." + ", specified=" + amVCores
                + ", max=" + maxVCores);
            amVCores = maxVCores;
        }

        // set the application name
        ApplicationSubmissionContext appContext = app.getApplicationSubmissionContext();
        ApplicationId appId = appContext.getApplicationId();

        appContext.setApplicationName(appName);

        // Set up resource type requirements
        // For now, both memory and vcores are supported, so we set memory and
        // vcores requirements
        Resource capability = Records.newRecord(Resource.class);
        capability.setMemorySize(amMemory);
        capability.setVirtualCores(amVCores);
        appContext.setResource(capability);

        // Set the priority for the application master
        Priority priority = Records.newRecord(Priority.class);
        priority.setPriority(amPriority);
        appContext.setPriority(priority);

        // Set the queue to which this application is to be submitted in the RM
        appContext.setQueue(amQueue);

        // Set the ContainerLaunchContext to describe the Container ith which the ApplicationMaster is launched.
        appContext.setAMContainerSpec(getAMContainerSpec(appId.getId()));

        // Submit the application to the applications manager
        // SubmitApplicationResponse submitResp = applicationsManager.submitApplication(appRequest);
        // Ignore the response as either a valid response object is returned on success
        // or an exception thrown to denote some form of a failure
        System.out.println("Submitting application to ASM");

        yarnClient.submitApplication(appContext);

        // Monitor the application
        return monitorApplication(appId);
    }

    private ContainerLaunchContext getAMContainerSpec(int appId) throws IOException {
        // Set up the container launch context for the application master
        ContainerLaunchContext amContainer = Records.newRecord(ContainerLaunchContext.class);

        FileSystem fs = FileSystem.get(conf);

        // set local resources for the application master
        // local files or archives as needed
        // In this scenario, the jar file for the application master is part of the local resources
        Map<String, LocalResource> localResources = new HashMap<>();

        System.out.println("Copy App Master jar from local filesystem and add to local environment");
        // Copy the application master jar to the filesystem
        // Create a local resource to point to the destination jar path
        addToLocalResources(fs, appMasterJarPath, Constants.AM_JAR_NAME, appId, localResources, null);

        // Set local resource info into app master container launch context
        amContainer.setLocalResources(localResources);

        // Set the env variables to be setup in the env where the application master will be run
        System.out.println("Set the environment for the application master");
        amContainer.setEnvironment(getAMEnvironment(localResources, fs));

        // Set the necessary command to execute the application master
        Vector<CharSequence> vargs = new Vector<>(30);

        // Set java executable command
        System.out.println("Setting up app master command");
        vargs.add(Environment.JAVA_HOME.$$() + "/bin/java");
        // Set Xmx based on am memory size
        vargs.add("-Xmx" + amMemory + "m");
        // Set class name
        vargs.add(MyApplicationMaster.class.getName());
        // Set params for Application Master
        vargs.add("--container_memory " + containerMemory);
        vargs.add("--container_vcores " + containerVirtualCores);
        vargs.add("--num_containers " + numContainers);
        vargs.add("--priority " + requestPriority);
        vargs.add("1>" + ApplicationConstants.LOG_DIR_EXPANSION_VAR + "/AppMaster.stdout");
        vargs.add("2>" + ApplicationConstants.LOG_DIR_EXPANSION_VAR + "/AppMaster.stderr");

        // Get final command
        StringBuilder command = new StringBuilder();
        for (CharSequence str : vargs) {
            command.append(str).append(" ");
        }

        System.out.println("Completed setting up app master command " + command);
        List<String> commands = new ArrayList<>();
        commands.add(command.toString());
        amContainer.setCommands(commands);

        return amContainer;
    }

    private void addToLocalResources(FileSystem fs, String fileSrcPath,
                                     String fileDstPath, int appId, Map<String, LocalResource> localResources,
                                     String resources) throws IOException {
        String suffix = appName + "/" + appId + "/" + fileDstPath;
        Path dst = new Path(fs.getHomeDirectory(), suffix);
        if (fileSrcPath == null) {
            FSDataOutputStream ostream = null;
            try {
                ostream = FileSystem
                    .create(fs, dst, new FsPermission((short) 0710));
                ostream.writeUTF(resources);
            } finally {
                IOUtils.closeQuietly(ostream);
            }
        } else {
            fs.copyFromLocalFile(new Path(fileSrcPath), dst);
        }
        FileStatus scFileStatus = fs.getFileStatus(dst);
        LocalResource scRsrc =
            LocalResource.newInstance(
                URL.fromURI(dst.toUri()),
                LocalResourceType.FILE, LocalResourceVisibility.APPLICATION,
                scFileStatus.getLen(), scFileStatus.getModificationTime());
        localResources.put(fileDstPath, scRsrc);
    }

    private Map<String, String> getAMEnvironment(Map<String, LocalResource> localResources, FileSystem fs) throws IOException {
        Map<String, String> env = new HashMap<>();

        // Set ApplicationMaster jar file
        LocalResource appJarResource = localResources.get(Constants.AM_JAR_NAME);
        Path hdfsAppJarPath = new Path(fs.getHomeDirectory(), appJarResource.getResource().getFile());
        FileStatus hdfsAppJarStatus = fs.getFileStatus(hdfsAppJarPath);
        long hdfsAppJarLength = hdfsAppJarStatus.getLen();
        long hdfsAppJarTimestamp = hdfsAppJarStatus.getModificationTime();

        env.put(Constants.AM_JAR_PATH, hdfsAppJarPath.toString());
        env.put(Constants.AM_JAR_TIMESTAMP, Long.toString(hdfsAppJarTimestamp));
        env.put(Constants.AM_JAR_LENGTH, Long.toString(hdfsAppJarLength));

        // Add AppMaster.jar location to classpath
        // At some point we should not be required to add
        // the hadoop specific classpaths to the env.
        // It should be provided out of the box.
        // For now setting all required classpaths including
        // the classpath to "." for the application jar
        StringBuilder classPathEnv = new StringBuilder(Environment.CLASSPATH.$$())
            .append(ApplicationConstants.CLASS_PATH_SEPARATOR).append("./*");
        for (String c : conf.getStrings(
            YarnConfiguration.YARN_APPLICATION_CLASSPATH,
            YarnConfiguration.DEFAULT_YARN_CROSS_PLATFORM_APPLICATION_CLASSPATH)) {
            classPathEnv.append(ApplicationConstants.CLASS_PATH_SEPARATOR);
            classPathEnv.append(c.trim());
        }
        env.put("CLASSPATH", classPathEnv.toString());

        return env;
    }

    /**
     * Monitor the submitted application for completion.
     * Kill application if time expires.
     *
     * @param appId Application Id of application to be monitored
     * @return true if application completed successfully
     * @throws org.apache.hadoop.yarn.exceptions.YarnException
     * @throws java.io.IOException
     */
    private boolean monitorApplication(ApplicationId appId)
        throws YarnException, IOException {

        while (true) {
            // Check app status every 1 second.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("Thread sleep in monitoring loop interrupted");
            }

            // Get application report for the appId we are interested in
            ApplicationReport report = yarnClient.getApplicationReport(appId);
            YarnApplicationState state = report.getYarnApplicationState();
            FinalApplicationStatus dsStatus = report.getFinalApplicationStatus();
            if (YarnApplicationState.FINISHED == state) {
                if (FinalApplicationStatus.SUCCEEDED == dsStatus) {
                    System.out.println("Application has completed successfully. "
                        + " Breaking monitoring loop : ApplicationId:" + appId.getId());
                    return true;
                } else {
                    System.out.println("Application did finished unsuccessfully."
                        + " YarnState=" + state.toString() + ", DSFinalStatus=" + dsStatus.toString()
                        + ". Breaking monitoring loop : ApplicationId:" + appId.getId());
                    return false;
                }
            } else if (YarnApplicationState.KILLED == state
                || YarnApplicationState.FAILED == state) {
                System.out.println("Application did not finish."
                    + " YarnState=" + state.toString() + ", DSFinalStatus=" + dsStatus.toString()
                    + ". Breaking monitoring loop : ApplicationId:" + appId.getId());
                return false;
            }

            if (System.currentTimeMillis() > (clientStartTime + clientTimeout)) {
                System.out.println("Reached client specified timeout for application. Killing application"
                    + ". Breaking monitoring loop : ApplicationId:" + appId.getId());
                forceKillApplication(appId);
                return false;
            }
        }
    }

    /**
     * Kill a submitted application by sending a call to the ASM
     *
     * @param appId Application Id to be killed.
     * @throws org.apache.hadoop.yarn.exceptions.YarnException
     * @throws java.io.IOException
     */
    private void forceKillApplication(ApplicationId appId)
        throws YarnException, IOException {
        yarnClient.killApplication(appId);

    }

    /**
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        boolean result = false;
        try {
            MyClient client = new MyClient();
            System.out.println("Initializing Client");
            try {
                boolean doRun = client.init(args);
                if (!doRun) {
                    System.exit(0);
                }
            } catch (IllegalArgumentException e) {
                System.err.println(e.getLocalizedMessage());
                client.printUsage();
                System.exit(-1);
            }
            result = client.run();
        } catch (Throwable t) {
            System.err.println("Error running CLient\n" + t.getMessage() + Arrays.toString(t.getStackTrace()));
            System.exit(1);
        }
        if (result) {
            System.out.println("Application completed successfully");
            System.exit(0);
        }
        System.err.println("Application failed to complete successfully");
        System.exit(2);
    }
}