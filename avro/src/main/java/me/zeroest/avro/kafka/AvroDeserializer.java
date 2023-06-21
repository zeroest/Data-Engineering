package me.zeroest.avro.kafka;

import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * https://taes-k.github.io/2021/02/05/avro/
 */
public class AvroDeserializer<T extends SpecificRecordBase> implements Deserializer<T> {
    private static final Logger log = LoggerFactory.getLogger(AvroDeserializer.class);

    protected final Class<T> targetType;

    public AvroDeserializer(Class<T> targetType) {
        this.targetType = targetType;
    }

    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public T deserialize(String topic, byte[] data) {
        T returnObject = null;

        if (data != null) {
            DatumReader<T> datumReader = new SpecificDatumReader<>(targetType);
            Decoder decoder = DecoderFactory.get().binaryDecoder(data, null);
            try {
                returnObject = datumReader.read(null, decoder);
            } catch (IOException e) {
                throw new IllegalStateException("Unable to Deserialize", e);
            }
        }

        return returnObject;
    }

    @Override
    public void close() {

    }
}
