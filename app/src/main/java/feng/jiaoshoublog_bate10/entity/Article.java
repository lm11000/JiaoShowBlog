package feng.jiaoshoublog_bate10.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by feng on 2016/6/30.
 */
public class Article implements Serializable {


    /**
     * id : 4
     * title : 服务器的环境配置（基于云服务器）
     * description : 记录一些如何在云服务器上使用 nginx +thin 运行 ROR 程序，包括 mysql 的配置 nginx的配置，部署的时候用的 thin database  secret等 YML
     * result_type : diary
     * cover : /uploads/result/cover/4/85aab65b-2314-4244-b84b-6f3e3fe91ada.jpg
     * items : [{"content":"首先安装 mysql   然后再试 nginx <br />\r\n1.mysql安装<br />\r\n2.nginx安装<br />\r\n3.设置 nginx 配置文件<br />\r\n4.设置 thin配置文件<br />\r\n5.设置 database 配置文件<br />","file_url":null}]
     */

    public int id;
    public String title;
    public String description;
    public String result_type;
    public String cover;
    /**
     * content : 首先安装 mysql   然后再试 nginx <br />
     * 1.mysql安装<br />
     * 2.nginx安装<br />
     * 3.设置 nginx 配置文件<br />
     * 4.设置 thin配置文件<br />
     * 5.设置 database 配置文件<br />
     * file_url : null
     */

    public List<ItemsBean> items;

    public static class ItemsBean implements Serializable {
        public String content;
        public Object file_url;


    }
}
