package MyCodec;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import com.misonet.model.EventClass;

public class EventClassListCodec implements Codec<EventClass>{

	@Override
	public void encode(BsonWriter writer, EventClass value, EncoderContext encoderContext) {
		writer.writeObjectId(new ObjectId(value.getId()));
		
		writer.writeStartDocument();
		 writer.writeString("_id", value.getId());
        writer.writeString("eventName", value.getEventName());
        writer.writeString("desc", value.getDesc());
        writer.writeString("location", value.getLocation());
        writer.writeString("interest", value.getInterest());
        writer.writeEndDocument();
	}

	@Override
	public Class<EventClass> getEncoderClass() {
		return EventClass.class;
	}

	@Override
	public EventClass decode(BsonReader reader, DecoderContext decoderContext) {
		reader.readStartDocument();
        EventClass value = new EventClass(reader.readString("eventName"), reader.readString("desc"), reader.readString("location"), reader.readString("interest"));
        reader.readEndDocument();
		return value;
	}

}
