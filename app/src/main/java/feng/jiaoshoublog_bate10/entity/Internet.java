package feng.jiaoshoublog_bate10.entity;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**此工具类封装网络请求
 * Created by feng on 2016/6/30.
 */
public class Internet {

    /**
     *这里封装了整个get方法，直接调用这个方法输入url可以直接获得内容
     *
     */

    OkHttpClient client = new OkHttpClient();
    public String getRun(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 这里封装的是post方法，formBody为表单形式的key、Value。通过参数将获得request（网络回传的内容），但是还需要okHttp来build
     * @param url
     * @param formBody
     * @return
     * @throws IOException
     */

   public Request postRun(String url, RequestBody formBody ) throws IOException {
//      RequestBody formBody = new FormBody.Builder()
//               .add(key,value)
//               .build();
       return new Request.Builder()
               .url(url)
               .post(formBody)
               .build();
    }
}

