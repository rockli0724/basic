package com.lj.basic.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

public class PinyinUtil
{
	private final static HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();

	static
	{
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
	}

	/**
	 * 将输入的汉字字符串转换成拼音,并在汉字之间填充fill所定义的字符串
	 * 
	 * @param str
	 *            "你好"
	 * @param fill
	 *            ~
	 * @return ni~hao
	 */
	public static String str2Pinyin(String str, String fill)
	{
		try
		{

			StringBuffer stringBuffer = new StringBuffer();
			if (fill == null)
				fill = "";
			boolean isCn = true;
			for (int i = 0; i < str.length(); i++)
			{
				char c = str.charAt(i);

				if (i > 0 && isCn)
				{

					stringBuffer.append(fill);
				}

				if (c == ' ')
				{
					stringBuffer.append(fill);
				}

				// 1 判断c是不是中文
				if (c >= '\u4e00' && c <= '\u9fa5')
				{
					isCn = true;
					stringBuffer.append(PinyinHelper.toHanyuPinyinStringArray(
							c, format)[0]);
				}
				else
				{
					// 不是中文
					if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
					{
						stringBuffer.append(c);
					}
					isCn = false;
				}
			}
			return stringBuffer.toString();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * obtain all initials of a word and put them into a String
	 * @param str
	 * @return an acronym 
	 */
	public static String getInitials(String str)
	{
		try
		{
			StringBuffer stringBuffer = new StringBuffer();
			for (int i = 0; i < str.length(); i++)
			{
				char c = str.charAt(i);
				// 1.判断c是不是中文
				if (c >= '\u4e00' && c <= '\u9fa5')
				{
					stringBuffer.append(PinyinHelper.toHanyuPinyinStringArray(
							c, format)[0].charAt(0));
				} 
			}
			return stringBuffer.toString();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

}
