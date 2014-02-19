
package com.haoyangmao.haoyangmao.common.net;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class HttpEntityFactroy {

    /**
     * 从json对象创建 HttpEntity
     * @param jsonObj
     * @return
     * @throws UnsupportedEncodingException
     */
    public static HttpEntity createEntityByJsonObj(JSONObject jsonObj) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(jsonObj.toString(), HTTP.UTF_8);
        entity.setContentType("application/json");
        return entity;
    }
}
