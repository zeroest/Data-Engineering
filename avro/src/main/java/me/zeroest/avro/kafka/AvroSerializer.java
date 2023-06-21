package me.zeroest.avro.kafka;

import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * https://taes-k.github.io/2021/02/05/avro/
 */
public class AvroSerializer<T extends SpecificRecordBase> implements Serializer<T> {
    private static final Logger log = LoggerFactory.getLogger(AvroSerializer.class);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, T payload) {
        byte[] bytes = null;

        if (payload != null) {
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                BinaryEncoder binaryEncoder = EncoderFactory.get().binaryEncoder(byteArrayOutputStream, null);
                DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(payload.getSchema());
                datumWriter.write(payload, binaryEncoder);
                binaryEncoder.flush();
                bytes = byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                throw new IllegalStateException("Unable to serialize", e);
            }
        }

        return bytes;
    }

    @Override
    public void close() {

    }
}
