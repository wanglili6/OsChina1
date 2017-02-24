package com.bestteam.oschina.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ywf on 2017/2/21.
 */
public class SpannableUtil {

    /**
     *
     * 格式化特殊的字符
     * 比如: <a href="http://xxxx">#java#</a>  或者  #java#
     * @param context
     * @param content
     * @return
     */
    public static Spannable formatterHtmlTag(final Context context, CharSequence content) {

        //<a href="http://xxxx">#java#</a>
        Pattern PatternSoftwareTagWithHtml = Pattern.compile(
                "<a\\s+href=['\"]([^'\"]*)['\"][^<>]*>(#[^#@<>\\s]+#)</a>"
        );


        return formatter(content, PatternSoftwareTagWithHtml, 1, 2, new Action1() {
            @Override
            public void call(String str) {
                Toast.makeText(context, "group = " + str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Spannable formatterOnlyTag(final Context context, CharSequence content) {

        //#java#
        Pattern PatternSoftwareTagWithHtml = Pattern.compile(
                "#([^#@<>\\\\s]+)#"
        );


        return formatterTag(context, content, new Action1() {
            @Override
            public void call(String group) {
                Toast.makeText(context, "group = " + group, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Spannable  formatterTag(final Context context, CharSequence content, final Action1  action1){
        SpannableStringBuilder builder = new SpannableStringBuilder(content);


        Pattern pattern = Pattern.compile(
                "#([^#@<>\\s]+)#"
        );

        Matcher matcher;
        while (true) {
            matcher = pattern.matcher(builder.toString());
            if (matcher.find()) {
                final String group0 = matcher.group(1);
                builder.replace(matcher.start(), matcher.end(), group0);
                ClickableSpan span = new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        action1.call(group0);
                    }
                };
                builder.setSpan(span, matcher.start(), matcher.start() + group0.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                continue;
            }
            break;
        }
        return builder;
    }


    /**
     * 格式化link链接
     *
     * @param context Context
     * @param content CharSequence
     * @return A spannable object
     */
    public static Spannable formatterOnlyLink(final Context context, CharSequence content) {

        //<a href="http://xxxx">xxx</a>
        Pattern PatternLinks = Pattern.compile("<a\\s+href=['\"]([^'\"]*)['\"][^<>]*>([^<>]*)</a>");

        return formatter(content, PatternLinks, 1, 2, new Action1() {
            @Override
            public void call(String str) {
            }
        });
    }

    /**
     * 格式化表情
     * @param res
     * @param spannable
     * @param size
     * @return
     */
    public static Spannable formatterEmoji(Resources res, Spannable spannable, int size) {
        String str = spannable.toString();
        // [微笑]  或者 :bowtie:
        Pattern pattern = Pattern.compile("(\\[[^\\[\\]:\\s\\n]+\\])|(:[^:\\[\\]\\s\\n]+:)");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            String emojiStr = matcher.group();
            if (TextUtils.isEmpty(emojiStr)) continue;
            //更加表情的内容,从表情库中,取出对应资源ID
            int resId =  EmojiHelper.getEmojiMap().get(emojiStr);
            if (resId <= 0) continue;
            //根据资源ID转换为Drawable对象
            Drawable drawable = res.getDrawable(resId);
            if (drawable == null) continue;
            drawable.setBounds(0, 0, size, size);

            //将表情资源设置给ImageSpan,用于显示表情
            ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
            spannable.setSpan(span, matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return spannable;
    }

    /**
     * 格式化内容
     *
     * @param sequence 处理内容
     * @param pattern  匹配规则
     * @param index0   使用的组号
     * @param index1   显示的组号
     * @param action   回调函数
     * @return Spannable
     */
    private static Spannable formatter(CharSequence sequence, Pattern pattern, int index0,
                                        int index1, final Action1 action) {
        SpannableStringBuilder builder = new SpannableStringBuilder(sequence);
        Matcher matcher;
        while (true) {
            matcher = pattern.matcher(builder.toString());
            if (matcher.find()) {
                final String group0 = matcher.group(index0);
                final String group1 = matcher.group(index1);
                builder.replace(matcher.start(), matcher.end(), group1);
                ClickableSpan span = new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        action.call(group0);
                    }
                };
                builder.setSpan(span, matcher.start(), matcher.start() + group1.length(),
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                continue;
            }
            break;
        }
        return builder;
    }

    public  interface Action1 {
        void call(String group);
    }
}
