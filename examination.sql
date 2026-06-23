-- ============================================================
-- examination 项目 MySQL 建表脚本
-- 根据 src/main/resources/mapper/*.xml 自动生成
-- 原始项目使用 Oracle (schema: EXAM)，已转换为 MySQL 语法
-- ============================================================

CREATE DATABASE IF NOT EXISTS examination DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE examination;

-- -----------------------------------------------------------
-- 1. APP_COURSE  课程表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS APP_COURSE;
CREATE TABLE APP_COURSE (
    ID          BIGINT       NOT NULL COMMENT '主键ID',
    NAME        VARCHAR(256) DEFAULT NULL COMMENT '课程名称',
    PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';

-- -----------------------------------------------------------
-- 2. APP_SCOPE  考试范围/计划表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS APP_SCOPE;
CREATE TABLE APP_SCOPE (
    ID               BIGINT       NOT NULL COMMENT '主键ID',
    NAME             VARCHAR(256) DEFAULT NULL COMMENT '名称',
    TYPE             VARCHAR(32)  DEFAULT NULL COMMENT '类型',
    STATUS           INT          DEFAULT NULL COMMENT '状态',
    ENABLED_FLAG     INT          DEFAULT 1 COMMENT '启用标志 1=启用 0=禁用',
    LAST_UPDATE_DATE DATETIME     DEFAULT NULL COMMENT '最后更新时间',
    LAST_UPDATED_BY  BIGINT       DEFAULT NULL COMMENT '最后更新人',
    CREATION_DATE    DATETIME     DEFAULT NULL COMMENT '创建时间',
    CREATED_BY       BIGINT       DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (ID),
    INDEX idx_app_scope_type (TYPE),
    INDEX idx_app_scope_status (STATUS)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考试范围/计划表';

-- -----------------------------------------------------------
-- 3. APP_SCOPE_COURSE_REL  范围-课程关联表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS APP_SCOPE_COURSE_REL;
CREATE TABLE APP_SCOPE_COURSE_REL (
    SCOPE_ID  BIGINT NOT NULL COMMENT '范围ID',
    COURSE_ID BIGINT NOT NULL COMMENT '课程ID',
    PRIMARY KEY (SCOPE_ID, COURSE_ID),
    INDEX idx_ascr_course (COURSE_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='范围-课程关联表';

-- -----------------------------------------------------------
-- 4. APP_SCOPE_USER_REL  范围-用户关联表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS APP_SCOPE_USER_REL;
CREATE TABLE APP_SCOPE_USER_REL (
    SCOPE_ID BIGINT NOT NULL COMMENT '范围ID',
    USER_ID  BIGINT NOT NULL COMMENT '用户ID',
    PRIMARY KEY (SCOPE_ID, USER_ID),
    INDEX idx_asur_user (USER_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='范围-用户关联表';

-- -----------------------------------------------------------
-- 5. EXAM_KEY_POINT  知识点表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS EXAM_KEY_POINT;
CREATE TABLE EXAM_KEY_POINT (
    COURSE_ID        BIGINT       NOT NULL COMMENT '课程ID',
    ID               BIGINT       NOT NULL COMMENT '主键ID',
    NAME             VARCHAR(256) DEFAULT NULL COMMENT '知识点名称',
    LAST_UPDATE_DATE DATETIME     DEFAULT NULL COMMENT '最后更新时间',
    LAST_UPDATED_BY  BIGINT       DEFAULT NULL COMMENT '最后更新人',
    CREATION_DATE    DATETIME     DEFAULT NULL COMMENT '创建时间',
    CREATED_BY       BIGINT       DEFAULT NULL COMMENT '创建人',
    ENABLED_FLAG     INT          DEFAULT 1 COMMENT '启用标志',
    MEMO             VARCHAR(512) DEFAULT NULL COMMENT '备注',
    STATUS           VARCHAR(32)  DEFAULT NULL COMMENT '状态',
    PARENT_ID        BIGINT       DEFAULT 0 COMMENT '父知识点ID',
    PRIMARY KEY (ID),
    INDEX idx_ekp_course (COURSE_ID),
    INDEX idx_ekp_parent (PARENT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识点表';

-- -----------------------------------------------------------
-- 6. EXAM_QUESTION  试题表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS EXAM_QUESTION;
CREATE TABLE EXAM_QUESTION (
    ID               BIGINT       NOT NULL COMMENT '主键ID',
    CODE             VARCHAR(64)  DEFAULT NULL COMMENT '试题编码',
    KEY_POINT_ID     BIGINT       DEFAULT NULL COMMENT '知识点ID',
    LEV              VARCHAR(16)  DEFAULT NULL COMMENT '难度等级',
    CONTENT          LONGTEXT     DEFAULT NULL COMMENT '试题内容(HTML)',
    TYPE             VARCHAR(32)  DEFAULT NULL COMMENT '题型(SINGLE/MULT/JUDGE/ESSAY等)',
    MEMO             VARCHAR(512) DEFAULT NULL COMMENT '备注',
    LAST_UPDATE_DATE DATETIME     DEFAULT NULL COMMENT '最后更新时间',
    LAST_UPDATED_BY  BIGINT       DEFAULT NULL COMMENT '最后更新人',
    CREATION_DATE    DATETIME     DEFAULT NULL COMMENT '创建时间',
    CREATED_BY       BIGINT       DEFAULT NULL COMMENT '创建人',
    ENABLED_FLAG     INT          DEFAULT 1 COMMENT '启用标志',
    PARSE            LONGTEXT     DEFAULT NULL COMMENT '试题解析',
    RANGE_TYPE       VARCHAR(32)  DEFAULT NULL COMMENT '适用范围类型',
    PRIMARY KEY (ID),
    INDEX idx_eq_kp (KEY_POINT_ID),
    INDEX idx_eq_type (TYPE),
    INDEX idx_eq_range (RANGE_TYPE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试题表';

-- -----------------------------------------------------------
-- 7. EXAM_ANSWER  答案表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS EXAM_ANSWER;
CREATE TABLE EXAM_ANSWER (
    ID               BIGINT       NOT NULL COMMENT '主键ID',
    CONTENT          LONGTEXT     DEFAULT NULL COMMENT '答案内容(HTML)',
    VALID            VARCHAR(1)   DEFAULT NULL COMMENT '是否为正确答案 1=是 0=否',
    LAST_UPDATE_DATE DATETIME     DEFAULT NULL COMMENT '最后更新时间',
    LAST_UPDATED_BY  BIGINT       DEFAULT NULL COMMENT '最后更新人',
    CREATION_DATE    DATETIME     DEFAULT NULL COMMENT '创建时间',
    CREATED_BY       BIGINT       DEFAULT NULL COMMENT '创建人',
    ENABLED_FLAG     INT          DEFAULT 1 COMMENT '启用标志',
    ANSWER_IDX       VARCHAR(8)   DEFAULT NULL COMMENT '答案序号(A/B/C/D等)',
    QUESTION_ID      BIGINT       DEFAULT NULL COMMENT '所属试题ID',
    PRIMARY KEY (ID),
    INDEX idx_ea_question (QUESTION_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='答案表';

-- -----------------------------------------------------------
-- 8. EXAM_POLICY  考试策略表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS EXAM_POLICY;
CREATE TABLE EXAM_POLICY (
    ID               BIGINT       NOT NULL COMMENT '主键ID',
    NAME             VARCHAR(256) DEFAULT NULL COMMENT '策略名称',
    TIME             INT          DEFAULT NULL COMMENT '考试时长(分钟)',
    TOTAL_SCORE      INT          DEFAULT NULL COMMENT '总分',
    PASS_SCORE       INT          DEFAULT NULL COMMENT '及格分',
    EXAM_TIME_START  DATETIME     DEFAULT NULL COMMENT '考试开始时间',
    EXAM_TIME_END    DATETIME     DEFAULT NULL COMMENT '考试结束时间',
    SHOW_ANSWER      VARCHAR(8)   DEFAULT NULL COMMENT '是否显示答案 YES/NO',
    SHOW_SCORE       VARCHAR(8)   DEFAULT NULL COMMENT '是否显示分数 YES/NO',
    SAVE_PAGE        VARCHAR(8)   DEFAULT NULL COMMENT '是否支持保存进度 YES/NO',
    IS_UNIFY         VARCHAR(8)   DEFAULT NULL COMMENT '是否统一试卷 YES/NO',
    MEMO             VARCHAR(512) DEFAULT NULL COMMENT '备注',
    LAST_UPDATE_DATE DATETIME     DEFAULT NULL COMMENT '最后更新时间',
    LAST_UPDATED_BY  BIGINT       DEFAULT NULL COMMENT '最后更新人',
    CREATION_DATE    DATETIME     DEFAULT NULL COMMENT '创建时间',
    CREATED_BY       BIGINT       DEFAULT NULL COMMENT '创建人',
    ENABLED_FLAG     INT          DEFAULT 1 COMMENT '启用标志',
    PAPER_COUNT      INT          DEFAULT NULL COMMENT '生成试卷份数',
    GENERATE_CYCLE   INT          DEFAULT NULL COMMENT '自动生成周期(分钟)',
    TYPE             VARCHAR(32)  DEFAULT NULL COMMENT '策略类型(AUTO/MANUAL)',
    PRIMARY KEY (ID),
    INDEX idx_ep_time (EXAM_TIME_START, EXAM_TIME_END),
    INDEX idx_ep_type (TYPE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考试策略表';

-- -----------------------------------------------------------
-- 9. EXAM_POLICY_DETAIL  考试策略明细表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS EXAM_POLICY_DETAIL;
CREATE TABLE EXAM_POLICY_DETAIL (
    ID               BIGINT      NOT NULL COMMENT '主键ID',
    EXAM_POLICY_ID   BIGINT      DEFAULT NULL COMMENT '策略ID',
    TYPE             VARCHAR(32) DEFAULT NULL COMMENT '题型',
    LEV              VARCHAR(16) DEFAULT NULL COMMENT '难度等级',
    QUESTION_COUNT   INT         DEFAULT NULL COMMENT '题目数量',
    PER_SCORE        INT         DEFAULT NULL COMMENT '每题分值',
    LAST_UPDATE_DATE DATETIME    DEFAULT NULL COMMENT '最后更新时间',
    LAST_UPDATED_BY  BIGINT      DEFAULT NULL COMMENT '最后更新人',
    CREATION_DATE    DATETIME    DEFAULT NULL COMMENT '创建时间',
    CREATED_BY       BIGINT      DEFAULT NULL COMMENT '创建人',
    ENABLED_FLAG     INT         DEFAULT 1 COMMENT '启用标志',
    PRIMARY KEY (ID),
    INDEX idx_epd_policy (EXAM_POLICY_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考试策略明细表';

-- -----------------------------------------------------------
-- 10. EXAM_PAPER  试卷表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS EXAM_PAPER;
CREATE TABLE EXAM_PAPER (
    ID               BIGINT       NOT NULL COMMENT '主键ID',
    EXAM_POLICY_ID   BIGINT       DEFAULT NULL COMMENT '策略ID',
    PAPER_URL        VARCHAR(512) DEFAULT NULL COMMENT '试卷URL',
    GUID             VARCHAR(64)  DEFAULT NULL COMMENT '试卷唯一标识',
    STATUS           VARCHAR(32)  DEFAULT NULL COMMENT '状态(YES/NO等)',
    USED             VARCHAR(8)   DEFAULT NULL COMMENT '是否已被使用',
    UPDATE_DATE      DATETIME     DEFAULT NULL COMMENT '更新时间',
    UPDATED_BY       BIGINT       DEFAULT NULL COMMENT '更新人',
    CREATION_DATE    DATETIME     DEFAULT NULL COMMENT '创建时间',
    CREATED_BY       BIGINT       DEFAULT NULL COMMENT '创建人',
    ENABLED_FLAG     INT          DEFAULT 1 COMMENT '启用标志',
    TIME             INT          DEFAULT NULL COMMENT '考试时长(分钟)',
    TOTLE_SCORE      INT          DEFAULT NULL COMMENT '总分',
    PASS_SCORE       INT          DEFAULT NULL COMMENT '及格分',
    PRIMARY KEY (ID),
    INDEX idx_ep_policy (EXAM_POLICY_ID),
    INDEX idx_ep_guid (GUID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试卷表';

-- -----------------------------------------------------------
-- 11. EXAM_PAPER_QUESTION_REL  试卷-试题关联表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS EXAM_PAPER_QUESTION_REL;
CREATE TABLE EXAM_PAPER_QUESTION_REL (
    PAPERS_ID   BIGINT NOT NULL COMMENT '试卷ID',
    QUESTION_ID BIGINT NOT NULL COMMENT '试题ID',
    SCORE       INT    DEFAULT NULL COMMENT '分值',
    PRIMARY KEY (PAPERS_ID, QUESTION_ID),
    INDEX idx_epqr_question (QUESTION_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试卷-试题关联表';

-- -----------------------------------------------------------
-- 12. EXAM_STUDENT_ANSWER  考生答卷表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS EXAM_STUDENT_ANSWER;
CREATE TABLE EXAM_STUDENT_ANSWER (
    ID               BIGINT       NOT NULL COMMENT '主键ID',
    STUDENT_ID       BIGINT       DEFAULT NULL COMMENT '考生用户ID',
    PAPERS_ID        BIGINT       DEFAULT NULL COMMENT '试卷ID',
    STATUS           INT          DEFAULT NULL COMMENT '状态 0=进行中 1=已提交',
    UPDATE_DATE      DATETIME     DEFAULT NULL COMMENT '更新时间',
    UPDATED_BY       BIGINT       DEFAULT NULL COMMENT '更新人',
    CREATION_DATE    DATETIME     DEFAULT NULL COMMENT '创建时间(开始考试时间)',
    CREATED_BY       BIGINT       DEFAULT NULL COMMENT '创建人',
    ENABLED_FLAG     INT          DEFAULT 1 COMMENT '启用标志',
    ANSWER_ID        LONGTEXT     DEFAULT NULL COMMENT '作答内容(序列化存储)',
    SCORE            INT          DEFAULT NULL COMMENT '客观题得分',
    REVIEW_STATUS    INT          DEFAULT 0 COMMENT '批阅状态 0=未批阅 1=已批阅',
    BUSINESS_INFO    VARCHAR(256) DEFAULT NULL COMMENT '业务信息',
    PRIMARY KEY (ID),
    INDEX idx_esa_student (STUDENT_ID),
    INDEX idx_esa_paper (PAPERS_ID),
    INDEX idx_esa_status (STATUS)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考生答卷表';

-- -----------------------------------------------------------
-- 13. EXAM_STUDENT_ANSWER_DETAIL  考生答卷明细表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS EXAM_STUDENT_ANSWER_DETAIL;
CREATE TABLE EXAM_STUDENT_ANSWER_DETAIL (
    ID                BIGINT   NOT NULL COMMENT '主键ID',
    STUDENT_ANSWER_ID BIGINT   DEFAULT NULL COMMENT '答卷ID',
    QUESTION_ID       BIGINT   DEFAULT NULL COMMENT '试题ID',
    ANSWER_CONTENT    LONGTEXT DEFAULT NULL COMMENT '作答内容',
    SCORE             INT      DEFAULT NULL COMMENT '得分',
    REVIEWER_ID       BIGINT   DEFAULT NULL COMMENT '批阅人ID',
    REVIEW_DATE       DATETIME DEFAULT NULL COMMENT '批阅时间',
    PRIMARY KEY (ID),
    INDEX idx_esad_sa (STUDENT_ANSWER_ID),
    INDEX idx_esad_question (QUESTION_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考生答卷明细表';

-- -----------------------------------------------------------
-- 14. EXAM_SCOPE_KEYPOINT_REL  范围-知识点关联表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS EXAM_SCOPE_KEYPOINT_REL;
CREATE TABLE EXAM_SCOPE_KEYPOINT_REL (
    SCOPE_ID    BIGINT NOT NULL COMMENT '范围ID',
    KEYPOINT_ID BIGINT NOT NULL COMMENT '知识点ID',
    PRIMARY KEY (SCOPE_ID, KEYPOINT_ID),
    INDEX idx_eskr_kp (KEYPOINT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='范围-知识点关联表';

-- -----------------------------------------------------------
-- 15. EXAM_SCOPE_POLICY_REL  范围-策略关联表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS EXAM_SCOPE_POLICY_REL;
CREATE TABLE EXAM_SCOPE_POLICY_REL (
    SCOPE_ID  BIGINT NOT NULL COMMENT '范围ID',
    POLICY_ID BIGINT NOT NULL COMMENT '策略ID',
    PRIMARY KEY (SCOPE_ID, POLICY_ID),
    INDEX idx_espr_policy (POLICY_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='范围-策略关联表';

-- -----------------------------------------------------------
-- 16. SYS_USER  系统用户表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS SYS_USER;
CREATE TABLE SYS_USER (
    ID               BIGINT       NOT NULL COMMENT '主键ID',
    ACCT             VARCHAR(64)  DEFAULT NULL COMMENT '登录账号',
    PWD              VARCHAR(128) DEFAULT NULL COMMENT '密码',
    REAL_NAME        VARCHAR(128) DEFAULT NULL COMMENT '真实姓名',
    MEMO             VARCHAR(512) DEFAULT NULL COMMENT '备注',
    EMAIL            VARCHAR(128) DEFAULT NULL COMMENT '邮箱',
    EMAIL_ACTIVE     VARCHAR(8)   DEFAULT NULL COMMENT '邮箱是否激活',
    STATUS           VARCHAR(32)  DEFAULT NULL COMMENT '状态',
    LAST_UPDATE_DATE DATETIME     DEFAULT NULL COMMENT '最后更新时间',
    LAST_UPDATED_BY  BIGINT       DEFAULT NULL COMMENT '最后更新人',
    CREATION_DATE    DATETIME     DEFAULT NULL COMMENT '创建时间',
    CREATED_BY       BIGINT       DEFAULT NULL COMMENT '创建人',
    ENABLED_FLAG     INT          DEFAULT 1 COMMENT '启用标志',
    LOGINS           INT          DEFAULT 0 COMMENT '登录次数',
    LAST_LOGIN_TIME  DATETIME     DEFAULT NULL COMMENT '最后登录时间',
    NICKNAME         VARCHAR(128) DEFAULT NULL COMMENT '昵称',
    ERRORTIMES       INT          DEFAULT 0 COMMENT '密码错误次数',
    LOGINTIME        DATETIME     DEFAULT NULL COMMENT '本次登录时间',
    PRIMARY KEY (ID),
    UNIQUE INDEX idx_su_acct (ACCT)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- -----------------------------------------------------------
-- 17. SYS_ROLE  系统角色表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS SYS_ROLE;
CREATE TABLE SYS_ROLE (
    ID               BIGINT       NOT NULL COMMENT '主键ID',
    NAME             VARCHAR(128) DEFAULT NULL COMMENT '角色名称',
    PARENT_ID        BIGINT       DEFAULT NULL COMMENT '父角色ID',
    DESCRIPTION      VARCHAR(512) DEFAULT NULL COMMENT '角色描述',
    STATUS           VARCHAR(32)  DEFAULT NULL COMMENT '状态',
    ENABLED_FLAG     INT          DEFAULT 1 COMMENT '启用标志',
    CREATED_BY       BIGINT       DEFAULT NULL COMMENT '创建人',
    CREATION_DATE    DATETIME     DEFAULT NULL COMMENT '创建时间',
    LAST_UPDATED_BY  BIGINT       DEFAULT NULL COMMENT '最后更新人',
    LAST_UPDATE_DATE DATETIME     DEFAULT NULL COMMENT '最后更新时间',
    PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';

-- -----------------------------------------------------------
-- 18. SYS_RIGHT  系统权限表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS SYS_RIGHT;
CREATE TABLE SYS_RIGHT (
    ID           BIGINT       NOT NULL COMMENT '主键ID',
    NAME         VARCHAR(128) DEFAULT NULL COMMENT '权限名称',
    PARENT_ID    BIGINT       DEFAULT NULL COMMENT '父权限ID',
    STATUS       VARCHAR(32)  DEFAULT NULL COMMENT '状态',
    URL          VARCHAR(256) DEFAULT NULL COMMENT '权限URL路径',
    CODE         VARCHAR(64)  DEFAULT NULL COMMENT '权限编码',
    SEQ          INT          DEFAULT NULL COMMENT '排序号',
    MEMO         VARCHAR(512) DEFAULT NULL COMMENT '备注',
    ENABLED_FLAG INT          DEFAULT 1 COMMENT '启用标志',
    PRIMARY KEY (ID),
    INDEX idx_sr_parent (PARENT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统权限表';

-- -----------------------------------------------------------
-- 19. SYS_USER_ROLE_REL  用户-角色关联表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS SYS_USER_ROLE_REL;
CREATE TABLE SYS_USER_ROLE_REL (
    USER_ID BIGINT NOT NULL COMMENT '用户ID',
    ROLE_ID BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (USER_ID, ROLE_ID),
    INDEX idx_surr_role (ROLE_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-角色关联表';

-- -----------------------------------------------------------
-- 20. SYS_ROLE_RIGHT_REL  角色-权限关联表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS SYS_ROLE_RIGHT_REL;
CREATE TABLE SYS_ROLE_RIGHT_REL (
    ROLE_ID  BIGINT NOT NULL COMMENT '角色ID',
    RIGHT_ID BIGINT NOT NULL COMMENT '权限ID',
    PRIMARY KEY (ROLE_ID, RIGHT_ID),
    INDEX idx_srrr_right (RIGHT_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-权限关联表';

-- ============================================================
-- 初始化数据 (可选)
-- ============================================================

-- 插入默认管理员用户 (密码需根据实际加密方式调整)
-- INSERT INTO SYS_USER (ID, ACCT, PWD, REAL_NAME, NICKNAME, STATUS, ENABLED_FLAG, CREATION_DATE)
-- VALUES (1, 'admin', 'admin123', '管理员', 'Admin', 'ACTIVE', 1, NOW());
