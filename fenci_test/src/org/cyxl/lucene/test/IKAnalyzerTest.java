package org.cyxl.lucene.test;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IKAnalyzerTest {
	public static void main(String[] args) {
		String str="";
		String filename = "H:\\matlab\\baidu\\dongfangzhixing_weibo_sample";
		File file=new File(filename);
		FileInputStream in=null;
		try {
			in=new FileInputStream(file);
			int size=in.available();
			byte[] buffer=new byte[size];
			in.read(buffer);
			str=new String(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally
		{
			if (in!=null)
			{
				try {
					in.close();
				} catch (Exception e1) {}
			}	
		}
		String regexp = "rt_text[^#]*([^\"]*)";
		Pattern pat=Pattern.compile(regexp);
		Matcher mat=pat.matcher(str);
		//while (mat.find()){System.out.println(mat.group(1));};
		ArrayList <String> tmp=new ArrayList<String>();
		while(mat.find()){tmp.add(mat.group(1));};
		String keyWord = convert(tmp.get(1));
		//String keyWord = "IKAnalyzer的分词效果到底怎么样呢，我们来看一下吧";
		//String keyWord="\u957f\u6c5f\u6c89\u8239\u4e1c\u65b9\u4e4b\u661f";
		String tmp1="\u4e1c";
		String keyWord0="#\u4e1c\u65b9\u4e4b\u661f\u6c89\u8239\u4e8b\u6545#\u3010\u957f\u6c5f\u5ba2\u8239\u7ffb\u6c89\u4e8b\u4ef6\u53d1\u5e03\u4f1a\uff1a\u521d\u5b9a\u4e24\u79cd\u6551\u63f4\u65b9\u6848\u3011\u2460\u622a\u81f3\u4eca\u592916\u70b9\uff0c\u5171\u641c\u6551\u523033\u4eba\uff0c14\u4eba\u751f\u8fd8\uff0c19\u4eba\u9047\u96be\uff1b\u2461\u9996\u652f\u5fc3\u7406\u6551\u63f4\u961f\u62b5\u8fbe\u76d1\u5229\uff1b\u2462\u6551\u63f4\u5175\u5206\u4e09\u8def\uff1a\u6c34\u4e0a\u3001\u6c34\u4e0b\u3001\u6cbf\u6c5f\u641c\u5bfb\uff1b\u2463\u66b4\u96e8\u6301\u7eed\uff0c\u6c34\u4e0b\u80fd\u89c1\u5ea6\u8f83\u5dee\uff1b\u8231\u5185\u5ea7\u6905\u98a0\u5012\u65e0\u5e8f\uff0c\u6709\u8231\u95e8\u88ab\u53cd\u9501\uff1b\u2464\u4e24\u79cd\u6551\u63f4\u65b9\u6848\uff1a\u6574\u4f53\u6253\u635e\u6216\u5207\u5272\u8239\u4f53\u3002";
		//创建IKAnalyzer中文分词对象
		IKAnalyzer analyzer = new IKAnalyzer();
		// 使用智能分词
		analyzer.setUseSmart(true);
		// 打印分词结果
		try {
			printAnalysisResult(analyzer, keyWord);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打印出给定分词器的分词结果
	 * 
	 * @param analyzer
	 *            分词器
	 * @param keyWord
	 *            关键词
	 * @throws Exception
	 */
	private static void printAnalysisResult(Analyzer analyzer, String keyWord)
			throws Exception {
		System.out.println("["+keyWord+"]分词效果如下");
		TokenStream tokenStream = analyzer.tokenStream("content",
				new StringReader(keyWord));
		tokenStream.addAttribute(CharTermAttribute.class);
		while (tokenStream.incrementToken()) {
			CharTermAttribute charTermAttribute = (CharTermAttribute) tokenStream.getAttribute(CharTermAttribute.class);
			System.out.println(charTermAttribute.toString());

		}
	}
	public static String convert(String utfString){
		StringBuilder sb = new StringBuilder();
		int i = -1;
		int pos = 0;
		
		while((i=utfString.indexOf("\\u", pos)) != -1){
			sb.append(utfString.substring(pos, i));
			if(i+5 < utfString.length()){
				pos = i+6;
				sb.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16));
			}
		}
		
		return sb.toString();
	}

}

