/**
 * 
 */
package org.apache.ibatis.scripting.xmltags;

import java.util.ArrayList;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;

/**
 * @author chenh
 * @date 2013年8月9日
 */
public class XMLDynamicLanguageDriver implements LanguageDriver {

	  public ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
	    return new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
	  }

	  public SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType) {
	    XMLScriptBuilder builder = new XMLScriptBuilder(configuration, script);
	    return builder.parseScriptNode();
	  }

	  public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
	    ArrayList<SqlNode> contents = new ArrayList<SqlNode>();
	    contents.add(new TextSqlNode(script.toString()));
	    MixedSqlNode rootSqlNode = new MixedSqlNode(contents);
	    return new DynamicSqlSource(configuration, rootSqlNode);
	  }
}
