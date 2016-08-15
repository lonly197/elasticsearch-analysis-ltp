package demo.lonly.stp.core;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * 分词接口
 * Created by Lonly on 2016/7/21.
 */
public interface ISegmenter {
    /**
     * 分词
     * @param target 待分词文本
     * @return 分词列表
     */
    List<String> segment(String target);
}
