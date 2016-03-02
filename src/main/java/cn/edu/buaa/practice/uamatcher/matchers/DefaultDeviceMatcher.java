/**
 * 
 */
package cn.edu.buaa.practice.uamatcher.matchers;

import cn.edu.buaa.practice.uamatcher.Matcher;
import cn.edu.buaa.practice.uamatcher.UserAgentInfo;
import cn.edu.buaa.practice.util.StringUtils;

/**
 * @author BurningIce
 *
 */
public class DefaultDeviceMatcher implements Matcher {
	private final String deviceName;
	private final String[] keywords;
	private final String keywordsPrefix;
	private final char[][] keywordsSuffix;
	private final int keywordsPrefixLength;
	private final boolean startsWith;
	
	public DefaultDeviceMatcher(String deviceName, String keywords) {
		this.deviceName = deviceName;
		this.keywords = (keywords == null || keywords.length() == 0? null : new String[] {keywords});
		this.keywordsPrefix = null;
		this.keywordsSuffix = null;
		this.keywordsPrefixLength = 0;
		this.startsWith = false;
	}
	
	public DefaultDeviceMatcher(String deviceName, String[] keywords) {
		this.deviceName = deviceName;
		this.keywords = (keywords == null || keywords.length == 0 ? null : keywords);
		this.keywordsPrefix = null;
		this.keywordsSuffix = null;
		this.keywordsPrefixLength = 0;
		this.startsWith = false;
	}
	
	public DefaultDeviceMatcher(String deviceName, String keywords, boolean startsWith) {
		this.deviceName = deviceName;
		this.keywords = (keywords == null || keywords.length() == 0? null : new String[] {keywords});
		this.keywordsPrefix = null;
		this.keywordsSuffix = null;
		this.keywordsPrefixLength = 0;
		this.startsWith = startsWith;
	}
	
	public DefaultDeviceMatcher(String deviceName, String[] keywords, boolean startsWith) {
		this.deviceName = deviceName;
		this.keywords = (keywords == null || keywords.length == 0 ? null : keywords);
		this.keywordsPrefix = null;
		this.keywordsSuffix = null;
		this.keywordsPrefixLength = 0;
		this.startsWith = startsWith;
	}
	
	public DefaultDeviceMatcher(String deviceName, String keywordsPrefix, String[] keywordsSuffix) {
		this.deviceName = deviceName;
		this.keywords = null;
		this.keywordsPrefix = (keywordsPrefix == null || keywordsPrefix.length() == 0 ? null : keywordsPrefix);
		this.keywordsPrefixLength = (keywordsPrefix == null || keywordsPrefix.length() == 0 ? 0 : keywordsPrefix.length());
		this.keywordsSuffix = (keywordsSuffix == null || keywordsSuffix.length == 0 ? null : new char[keywordsSuffix.length][]);
		if(keywordsSuffix != null && keywordsSuffix.length != 0) {			
			for(int i = 0; i < keywordsSuffix.length; ++i) {
				this.keywordsSuffix[i] = keywordsSuffix[i].toCharArray();
			}
		}
		this.startsWith = false;
	}
	
	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#match(java.lang.String, cn.edu.buaa.practice.uamatcher.UserAgentInfo, int)
	 */
	public void match(String userAgent, UserAgentInfo userAgentInfo, int beginIndex) {
		userAgentInfo.setBrandName(this.deviceName);
	}

	/* (non-Javadoc)
	 * @see cn.edu.buaa.practice.uamatcher.Matcher#tryMatch(java.lang.String)
	 */
	public int tryMatch(String userAgent) {
		if(keywords != null) {
			if(this.startsWith) {
				return StringUtils.startsWithAny(userAgent, this.keywords) ? 0 : -1;
			} else {
				return StringUtils.indexOfAny(userAgent, this.keywords);
			}
		} else if(keywordsPrefix != null) {
			int k = userAgent.indexOf(keywordsPrefix);
			if(k != -1) {
				for(int i = 0; i < keywordsSuffix.length; ++i) {
					if(StringUtils.followWith(userAgent, keywordsSuffix[i], k + keywordsPrefixLength)) {
						return k;
					}
				}
			}
		}
		
		return -1;
	}
}
