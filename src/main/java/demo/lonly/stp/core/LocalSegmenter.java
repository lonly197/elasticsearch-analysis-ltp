package demo.lonly.stp.core;

import demo.lonly.stp.cfg.Configuration;
import edu.hit.ir.ltp4j.Segmentor;
import org.elasticsearch.SpecialPermission;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.ESLoggerFactory;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

/**
 * 本地分词实现
 * Created by Lonly on 2016/7/21.
 */
public class LocalSegmenter {
    private static ESLogger logger = ESLoggerFactory.getLogger("LocalSegmenter");

    //private static String default_path = Configuration.getRoot() + "cws.model";
    //private List<String> words = new ArrayList<String>();

    static{
        if (Segmentor.create("/home/systex/ltp_project/models/cws.model",
                "/home/systex/ltp_project/models/zhengwu.model") < 0) {
            logger.error("LTP Model Load Failed");
        }else{
            logger.info("LTP Model Load Successed");
        }
    }

    /*public LocalSegmenter(String path) {
        this.init(path);
    }*/

    /*private void init(String path){
        int loadedModelNum;
        if (Strings.isNullOrEmpty(path)) {
            loadedModelNum = Segmentor.create(default_path);
        } else {
            String[] paths = path.split(";");
            int size = paths.length;
            switch (size) {
                case 1:
                    loadedModelNum = Segmentor.create(paths[0]);
                    break;
                default:
                    loadedModelNum = Segmentor.create(paths[0], paths[1]);
                    break;
            }
        }
        if (loadedModelNum < 0) {
            logger.error("LTP Model Load Failed");
        } else {
            logger.info("LTP Model Load Successed");
        }
    }*/

    //@Override
    public static List<String> segment(String target) {
        // Clean the word token
        List<String> words = new ArrayList<String>();
        // Get the new word token of target
        Segmentor.segment(target, words);
        return words;
    }
}
