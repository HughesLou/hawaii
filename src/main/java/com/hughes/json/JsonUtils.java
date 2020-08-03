package com.hughes.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author hugheslou
 * Created on 2020/6/22.
 */
public class JsonUtils {

    public static void main(String[] args) throws Exception {
        String result = "{\"metadata\":{\"timestamp_utc\":\"2020-06-18 12:15:30\",\"music\":[{\"label\":\"维音唱片\","
                + "\"play_offset_ms\":6260,\"external_ids\":{},\"artists\":[{\"name\":\"王妮娜\"}],"
                + "\"result_from\":3,\"acrid\":\"d52efd9ae9298f364dcf1b2196848e06\",\"title\":\"美人吟\","
                + "\"duration_ms\":250000,\"album\":{\"name\":\"美人吟\"},\"score\":100,\"external_metadata\":{},"
                + "\"release_date\":\"2016-01-20\"},{\"play_offset_ms\":6240,\"external_ids\":{},"
                + "\"artists\":[{\"name\":\"钟心\"}],\"acrid\":\"340a656878be01ba26f2f23d98527914\","
                + "\"title\":\"美人吟\",\"duration_ms\":239000,\"album\":{\"name\":\"美人吟\"},\"score\":100,"
                + "\"external_metadata\":{},\"result_from\":3},{\"label\":\"广州音像\",\"play_offset_ms\":6260,"
                + "\"external_ids\":{},\"artists\":[{\"name\":\"李玲玉\"}],\"result_from\":3,"
                + "\"acrid\":\"701f53116f53bb3d334ed3826fdd6d2b\",\"title\":\"美人吟\",\"duration_ms\":241000,"
                + "\"album\":{\"name\":\"美人吟\"},\"score\":100,\"external_metadata\":{},"
                + "\"release_date\":\"2005-09-01\"}]},\"cost_time\":0.017999887466431,"
                + "\"status\":{\"msg\":\"Success\",\"version\":\"1.0\",\"code\":0},\"result_type\":0}";

        JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
        JsonArray musics = jsonObject.getAsJsonObject("metadata").getAsJsonArray("music");
        for (JsonElement jsonElement : musics) {
            JsonObject music =  jsonElement.getAsJsonObject();
            String acrId = music.get("acrid").getAsString();
            music.addProperty("test", acrId);
            System.out.println("acrId=" + acrId);
        }

        System.out.println("result=" + jsonObject.toString());

        jsonObject.getAsJsonObject("metadata").getAsJsonArray("music");


        String test = "{\"identity\":{\"item_id\":\"1000296931723324222\",\"producer\":\"cp_mmu\","
                + "\"create_timestamp\":\"1591901360135\"},\"is_valid\":true,\"expire_time\":\"2030-01-01 00:00:00\","
                + "\"normalized_content\":\"\\u003c!--{audio:0}--\\u003e\",\"title\":\"歌单分类-一往知情深\","
                + "\"content\":\"\\u003c!--{audio:0}--\\u003e\",\"source\":\"cp_mmu_抖音_歌单分类_国风\","
                + "\"orig_source\":\"云の泣\",\"original_url\":\"https://api.amemv.com/6766565733540629255\","
                + "\"job_uuid\":\"4689487997129112868\",\"doc_ext_kvs\":{\"t_real_url\":\"https://api.amemv"
                + ".com/aweme/v1/music/list/?mc_id\\u003d20\\u0026cursor\\u003d90\\u0026count\\u003d30"
                + "\\u0026js_sdk_version\\u003d\\u0026app_type\\u003dnormal\\u0026openudid\\u003d30D9D9EEA7CD0000"
                + "\\u0026version_name\\u003d6.5.0\\u0026device_type\\u003dvivo%20Y18L\\u0026ssmix\\u003da\\u0026iid"
                + "\\u003d107526734643\\u0026os_api\\u003d19\\u0026mcc_mnc\\u003d46007\\u0026device_id"
                + "\\u003d56770896929\\u0026resolution\\u003d900*1440\\u0026device_brand\\u003dvivo\\u0026aid"
                + "\\u003d1128\\u0026manifest_version_code\\u003d650\\u0026app_name\\u003daweme\\u0026os_version"
                + "\\u003d4.4.2\\u0026device_platform\\u003dandroid\\u0026version_code\\u003d650"
                + "\\u0026update_version_code\\u003d6502\\u0026ac\\u003dwifi\\u0026dpi\\u003d320\\u0026uuid"
                + "\\u003d355888888888888\\u0026language\\u003dzh\\u0026channel\\u003dwandoujia_aweme1\","
                + "\"t_jwrapper_extract_result_all\":\"{\\\"audio_duration\\\":\\\"30\\\",\\\"name\\\":\\\"一往知情深\\\","
                + "\\\"audio_media_url\\\":\\\"http://p1-dy.byteimg.com/obj/ies-music/1651993620959240.mp3\\\","
                + "\\\"audio\\\":\\\"contains_audio\\\",\\\"title\\\":\\\"歌单分类-一往知情深\\\","
                + "\\\"audio_poster\\\":\\\"https://p1-dy-ipv6.byteimg"
                + ".com/aweme/1080x1080/ies-music/storm_cover_547c89ec0261617e0bc2f5f8145d7a5c.jpeg\\\","
                + "\\\"key\\\":\\\"6766565733540629255\\\",\\\"source_name\\\":\\\"云の泣\\\"}\","
                + "\"t_link_item\":\"{\\\"specialData\\\":null,\\\"url\\\":\\\"https://api.amemv"
                + ".com/aweme/v1/music/list/?mc_id\\u003d20\\u0026cursor\\u003d90\\u0026count\\u003d30"
                + "\\u0026js_sdk_version\\u003d\\u0026app_type\\u003dnormal\\u0026openudid\\u003d30D9D9EEA7CD0000"
                + "\\u0026version_name\\u003d6.5.0\\u0026device_type\\u003dvivo%20Y18L\\u0026ssmix\\u003da\\u0026iid"
                + "\\u003d107526734643\\u0026os_api\\u003d19\\u0026mcc_mnc\\u003d46007\\u0026device_id"
                + "\\u003d56770896929\\u0026resolution\\u003d900*1440\\u0026device_brand\\u003dvivo\\u0026aid"
                + "\\u003d1128\\u0026manifest_version_code\\u003d650\\u0026app_name\\u003daweme\\u0026os_version"
                + "\\u003d4.4.2\\u0026device_platform\\u003dandroid\\u0026version_code\\u003d650"
                + "\\u0026update_version_code\\u003d6502\\u0026ac\\u003dwifi\\u0026dpi\\u003d320\\u0026uuid"
                + "\\u003d355888888888888\\u0026language\\u003dzh\\u0026channel\\u003dwandoujia_aweme1\\\","
                + "\\\"distinctedSeedId\\\":null,\\\"html\\\":\\\"\\\",\\\"anchorList\\\":null}\"},"
                + "\"audio_meta_settings\":[{\"play_length\":30,\"poster\":\"https://p1-dy-ipv6.byteimg"
                + ".com/aweme/1080x1080/ies-music/storm_cover_547c89ec0261617e0bc2f5f8145d7a5c.jpeg\","
                + "\"web_page_url\":\"https://api.amemv.com/6766565733540629255\",\"category\":\"VPS\","
                + "\"play_page_url\":\"http://p1-dy.byteimg.com/obj/ies-music/1651993620959240.mp3\","
                + "\"audio_id\":\"9303152572223014988\"}]}";

        String audioId = new JsonParser().parse(test).getAsJsonObject().getAsJsonArray
                ("audio_meta_settings").get(0).getAsJsonObject().get("audio_id").getAsString();

        //            new JSONObject(result).getJSONArray("metadata").getJSONArray("music").getJSONObject(0)
        //            .getString("audio_id");


        System.exit(0);
    }
}
