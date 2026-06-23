/**
 * 
 */
package com.vnetoo.examination;

/**
 * @author chenh
 *
 */
public class ExamConstant {
	private ExamConstant(){}
	
	//单选
	public static final int EXAM_TYPE_MONOMIAL = 30;
	//多选
	public static final int EXAM_TYPE_MORE = 31;
	//判断
	public static final int EXAM_TYPE_OPIN = 29;
	
	
	
	public static final String KEY_APP_PATH				= "key_app_path";
	
	public static final String KEY_EXAM_MODULE_MAPPING	= "exam_module_mapping";
	public static final String KEY_EXAM_ORACLE_MAPPING 	= "exam_oracle_mapping";
	public static final String KEY_FREEMARKER_TEMPLATE_PATH = "freemarker_template_path";
	public static final String KEY_DATABASE_DRIVER 		= "database_driver";
	
	public static final String KEY_ENCODING				= "encoding";
	public static final String KEY_CACHE_ENGIN			= "cache_engin";
	public static final String KEY_CACHEABLE_OBJECTS	= "cacheable_objects";
	
	public static final String KEY_SERVLET_URI 			= "servlet_uri";
	public static final String KEY_BEAN_ID_DATACOURCE 	= "bean_id_datacource";
	public static final String KEY_BEAN_ID_DAO 			= "bean_id_dao";
	public static final String KEY_EXAM_PAPER_PATH 		= "exam_paper_path";
	
	public static final String KEY_SYCHRO_PAPER 			= "sychro_paper";
	public static final String KEY_SYCHRO_PAPER_SERVER_IP 	= "sychro_paper_server_ip";
	public static final String KEY_SYCHRO_PAPER_SERVER_PORT = "sychro_paper_server_port";
	
	public static final int RESPONSE_SUCCESS = 0;
	public static final int RESPONSE_FAILURE = -1;
	
	public static final String PAPER_DIR = "paper_dir";
	public static final String STUDENT_AUTH = "student_auth";
	public static final String INTEGRATED_RUNNING = "integrated_running";
	public static final String VALID_STUDENT_URL = "valid_student_url";
	public static final String FETCH_STUDENT_INFO_URL = "fetch_student_info_url";
	public static final String INIT_PASSWORD = "init_password";
}
