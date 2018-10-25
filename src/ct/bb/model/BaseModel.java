package ct.bb.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

public class BaseModel implements Serializable {

	protected static final JsonConfig jsonConfig = new JsonConfig();

	static {
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {

			@Override
			public Object processObjectValue(String key, Object value, JsonConfig arg2) {

				if (value == null) {
					return null;
				} else if (value.equals("")) {
					return null;
				}
				return value.toString();
			}

			@Override
			public Object processArrayValue(Object paramObject, JsonConfig paramJsonConfig) {
				return null;
			}
		});
		jsonConfig.registerDefaultValueProcessor(String.class, new DefaultValueProcessor() {
			@Override
			public Object getDefaultValue(Class type) {
				return null;
			}
		});
	}

	public BaseModel() {
	}

	public static BaseModel toModel(String jsonString, Class wmClass, Map<String, Class> map) {
		BaseModel returnBaseModel = null;
		try {
			JSONObject jsonObject = JSONObject.fromObject(jsonString, jsonConfig);
			returnBaseModel = (BaseModel) JSONObject.toBean(jsonObject, wmClass, map);
		} catch (Exception e) {
			// throw new BadDataFormatExcption();
			e.printStackTrace();
		}
		return returnBaseModel;
	}

	public static BaseModel toModel(String jsonString, Class bmClass) {
		BaseModel returnBaseModel = null;
		JSONObject jsonObject = null;
		try {
			jsonObject = JSONObject.fromObject(jsonString, jsonConfig);
			returnBaseModel = (BaseModel) JSONObject.toBean(jsonObject, bmClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnBaseModel;
	}

	/**
	 * 转换返回Model为一个JSON字符串，这里还没有进行GZIP压缩，不是最终传给用户的数据
	 * 
	 * @return
	 */
	public String toJsonString() {
		JSONObject jsonObject = JSONObject.fromObject(this, jsonConfig);
		return jsonObject.toString();
	}

	public JSONObject toJsonObject() {
		JSONObject jsonObject = JSONObject.fromObject(this, jsonConfig);
		return jsonObject;
	}

}