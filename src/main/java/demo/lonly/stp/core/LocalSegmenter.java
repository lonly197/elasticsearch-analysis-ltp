package demo.lonly.stp.core;

import edu.hit.ir.ltp4j.Segmentor;
import org.elasticsearch.SpecialPermission;
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
public final class LocalSegmenter implements ISegmenter {
    private static ESLogger logger = ESLoggerFactory.getLogger("LocalSegmenter");

    //private static String default_path = Configuration.getRoot() + "cws.model";
    //private List<String> words = new ArrayList<String>();

    static {
        logger.info("Grant Permission");
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            // unprivileged code such as scripts do not have SpecialPermission
            sm.checkPermission(new SpecialPermission());
        }
        AccessController.doPrivileged(
            // sensitive operation
            new PrivilegedAction<Boolean>() {
                public Boolean run() {
                    logger.info("PrivilegedAction Load Model");
                    LoadModel();
                    return Boolean.TRUE;
                }
            });

    }

    private static void LoadModel() {
        logger.error("LTP Model Load Starting......");
        // 加载模型
        if (Segmentor.create("/home/systex/ltp_project/models/cws.model",
            "/home/systex/ltp_project/models/zhengwu.model") < 0) {
            logger.error("LTP Model Load Failed");
        } else {
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
    public List<String> segment(String target) {
        // Clean the word token
        List<String> words = new ArrayList<String>();
        // Get the new word token of target
        Segmentor.segment(target, words);
        return words;
    }
}
