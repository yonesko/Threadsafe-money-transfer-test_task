package glebio.bank.api;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.UUID;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

/**
 * @author Gleb Danichev
 */
public class UUIDMessageBodyWriter implements MessageBodyWriter<UUID> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type == UUID.class;
    }

    @Override
    public void writeTo(UUID uuid, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
        MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException,
        WebApplicationException
    {
        entityStream.write(uuid.toString().getBytes());
    }
}
