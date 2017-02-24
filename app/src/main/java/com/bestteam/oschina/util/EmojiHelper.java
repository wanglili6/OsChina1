package com.bestteam.oschina.util;

import com.bestteam.oschina.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ywf on 2017/2/21.
 */
public class EmojiHelper {

    public static Map<String, Integer>getEmojiMap(){
        Map<String, Integer>  emojiMap = new HashMap<>();
        emojiMap.put("[微笑]",  R.drawable.smiley_0);
        emojiMap.put("[撇嘴]",  R.drawable.smiley_1);
        emojiMap.put("[色]",  R.drawable.smiley_2);
        emojiMap.put("[发呆]",  R.drawable.smiley_3);
        emojiMap.put("[得意]",  R.drawable.smiley_4);
        emojiMap.put("[流泪]",  R.drawable.smiley_5);


        emojiMap.put(":bowtie:",  R.drawable.bowtie);
        emojiMap.put(":smile:",  R.drawable.smile);
        emojiMap.put(":laughing:",  R.drawable.laughing);
        emojiMap.put(":blush:",  R.drawable.blush);
        emojiMap.put(":smiley:",  R.drawable.smiley);

        return emojiMap;
    }
}
