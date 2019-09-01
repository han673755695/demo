package com.example.demo.common;

import java.io.File;

/**
 * 全局的静态变量,用于全局变量的存放
 * 
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version 2013-03-19 11:08:15
 * @see org.springrain.frame.util.GlobalStatic
 */
public class GlobalStatic {

	private GlobalStatic() {
		throw new IllegalAccessError("工具类不能实例化");
	}

	public static String rootDir = null;
	public static String webInfoDir = null;
	public static String staticHtmlDir = null;
	public static String tempRootpath = System.getProperty("user.dir") + "/temp/";
	public static final int excelPageSize = 1000;
	public static final String suffix = ".html";
	public static final String excelext = ".xls";
	public static final String exportexcel = "exportexcel";// 是否是导出操作的key
	public static final String dataUpdate = "更新";
	public static final String dataSave = "保存";
	public static final String dataDelete = "删除";

	public static final String SQLCutSeparator = "___";// SQL复合对象查询的分隔符,三个 下划线

	public static final String projectKeyPrefix = "kindergarten_";// ES和redis的固定前缀,用于多个项目使用同一个ES和redis集群

	public static final String mpConfigCacheKey = "mpConfigCacheKey";// 订阅号配置缓存Key

	public static final String cpConfigCacheKey = "cpConfigCacheKey";// 企业号配置缓存Key

	public static final String xcxConfigCacheKey = "xcxConfigCacheKey";// 小程序配置缓存Key

	// page对象的缓存后缀key
	public static final String pageCacheExtKey = "_springrain_page_key";

	// 主业务缓存
	public static final String cacheKey = "springraincache";
	// 权限缓存
	public static final String qxCacheKey = "springrainqxcache";
	// 页面静态化缓存
	// public static final String staticHtmlCacheKey="statichtmlcache";
	// 登录次数校验缓存
	public static final String springrainloginCacheKey = "springrainlogincache";
	// 缓存用户最后有效的登录sessionId
	public static final String springrainkeeponeCacheKey = "springrainkeeponecache";
	// 防火墙缓存
	public static final String springrainfirewallCacheKey = "springrainfriewallcache";
	// 微信缓存
	// public static final String springrainweixinCacheKey="springrainweixincache";
	// cms 缓存
	public static final String springraincmsCacheKey = "springraincmscache";

	// defaultSiteId 缓存
	public static final String springraindefaultSiteId = "defaultSiteId";

	// 前后台传递的tokenKey
	public static final String tokenKey = "springraintoken";
	// 如果token错误,跳转地址的key
	public static final String errorTokentoURLKey = "errorspringraintokentourlkey";
	// token错误跳转的页面
	public static final String errorTokentoURL = "/errorpage/tokenerror";

	// 自定义的登录地址key
	public static final String customLoginURLKey = "customLoginURLKey";

	public static final String defaultCharset = "UTF-8";

	public static final String tableSuffix = "_history_";
	public static final String tableSuffixSymbol = "_";
	public static final String frameTableAlias = "frameTableAlias";
	public static final String returnDatas = "returnDatas";

	// 认证
	// public static final String reloginsession="shiro-reloginsession";
	// 认证
	public static final String authenticationCacheName = "shiro-authenticationCacheName";
	// 授权
	public static final String authorizationCacheName = "shiro-authorizationCacheName";
	// realm名称
	public static final String authorizingRealmName = "shiroDbAuthorizingRealmName";

	

}
