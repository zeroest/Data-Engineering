# Data Engineering

Data engineers primarily focus on
- Build and maintain the organization’s data pipeline systems
- Clean and wrangle data into a usable state

[Data engineering: A quick and simple definition](https://www.oreilly.com/content/data-engineering-a-quick-and-simple-definition)

## Real-time streaming system

| Type | Delay             |
|------|-------------------|
| Hard | micros ~ millis   |
| Soft | millis ~ seconds  |
| Near | seconds ~ minutes |

[Difference between Hard real time and Soft real time system](https://www.geeksforgeeks.org/difference-between-hard-real-time-and-soft-real-time-system/)

## [Data Streaming Architecture: Components, Process, & Diagrams](https://estuary.dev/data-streaming-architecture/)

### ETL Pipeline

- Extract
- Transform
- Load

### The Data Streaming Process Explained

1. Data Production
2. Data Ingestion
3. Data Processing
4. Streaming Data Analytics
5. Data Reporting
6. Data Visualization & Decision Making

### 5 Main Components Of Data Streaming Architecture

A. Stream Processing Engine  
B. Message Broker  
C. Data Storage  
D. Data Ingestion Layer  
E. Data Visualization & Reporting Tools  

### Architecture

Lambda architecture
- batch, speed, serving layer

Kappa architecture, Delta architecture
- speed, serving layer

[[IT용어](데이터 흐름) 기간계, 정보계, ODS(Operational Data Store), EDW(Enterprise Data Warehouse), Data Mart(DM), OLAP(On line Analysis Process), ETL(Extract Transform Load),ELT(Extract Load Transform)](https://spidyweb.tistory.com/218)
