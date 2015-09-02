--
-- ER/Studio Data Architect 9.0 SQL Code Generation
-- Project :      Master.DM1
--
-- Date Created : Monday, January 31, 2011 14:56:13
-- Target DBMS : MySQL 5.x
--

-- 
-- TABLE: FunctionalArea 
--

CREATE TABLE FunctionalArea(
    _FunctionalArea_key    INT              NOT NULL,
    FunctionalArea         VARCHAR(75)      NOT NULL,
    Description            VARCHAR(1000),
    CreatedBy              VARCHAR(18)      NOT NULL,
    DateCreated            DATETIME         NOT NULL,
    ModifiedBy             VARCHAR(18)      NOT NULL,
    DateModified           DATETIME         NOT NULL,
    Version                INT              DEFAULT 1 NOT NULL,
    PRIMARY KEY (_FunctionalArea_key)
)ENGINE=INNODB
;



-- 
-- TABLE: User 
--

CREATE TABLE User(
    _User_key                INT             AUTO_INCREMENT,
    _DefaultWorkgroup_key    INT             NOT NULL,
    NetworkID                VARCHAR(18)     NOT NULL,
    UserName                 VARCHAR(128),
    Password_                VARCHAR(40),
    FirstName                VARCHAR(18)     NOT NULL,
    LastName                 VARCHAR(18)     NOT NULL,
    Title                    VARCHAR(18),
    EmailAddress             VARCHAR(18),
    InternalPhone            VARCHAR(18),
    ExternalPhone            VARCHAR(18),
    HireDate                 DATE,
    IsMasterAdministrator    TINYINT,
    IsActive                 TINYINT,
    CreatedBy                VARCHAR(18)     NOT NULL,
    DateCreated              DATETIME        NOT NULL,
    ModifiedBy               VARCHAR(18)     NOT NULL,
    DateModified             DATETIME        NOT NULL,
    Version                  INT             DEFAULT 1 NOT NULL,
    PRIMARY KEY (_User_key)
)ENGINE=INNODB
;



-- 
-- TABLE: Workgroup 
--

CREATE TABLE Workgroup(
    _Workgroup_key    INT            AUTO_INCREMENT,
    WorkgroupName     VARCHAR(75)    NOT NULL,
    IsActive          TINYINT,
    CreatedBy         VARCHAR(18)    NOT NULL,
    DateCreated       DATETIME       NOT NULL,
    ModifiedBy        VARCHAR(18)    NOT NULL,
    DateModified      DATETIME       NOT NULL,
    Version           INT            DEFAULT 1 NOT NULL,
    PRIMARY KEY (_Workgroup_key)
)ENGINE=INNODB
;



-- 
-- TABLE: WorkgroupUser 
--

CREATE TABLE WorkgroupUser(
    _WorkgroupUser_key    INT            AUTO_INCREMENT,
    _Workgroup_key        INT            NOT NULL,
    _User_key             INT            NOT NULL,
    CreatedBy             VARCHAR(18)    NOT NULL,
    DateCreated           DATETIME       NOT NULL,
    ModifiedBy            VARCHAR(18)    NOT NULL,
    DateModified          DATETIME       NOT NULL,
    Version               INT            DEFAULT 1 NOT NULL,
    PRIMARY KEY (_WorkgroupUser_key)
)ENGINE=INNODB
;



-- 
-- TABLE: WorkgroupUserFunctionalArea 
--

CREATE TABLE WorkgroupUserFunctionalArea(
    _WorkgroupUserFunctionalArea    INT            NOT NULL,
    _FunctionalArea_key             INT            NOT NULL,
    _WorkgroupUser_key              INT            NOT NULL,
    CreatedBy                       VARCHAR(18)    NOT NULL,
    DateCreated                     DATETIME       NOT NULL,
    ModifiedBy                      VARCHAR(18)    NOT NULL,
    DateModified                    DATETIME       NOT NULL,
    Version                         INT            DEFAULT 1 NOT NULL,
    PRIMARY KEY (_WorkgroupUserFunctionalArea)
)ENGINE=INNODB
;



-- 
-- INDEX: Ref181592 
--

CREATE INDEX Ref181592 ON User(_DefaultWorkgroup_key)
;
-- 
-- INDEX: Ref181295 
--

CREATE INDEX Ref181295 ON WorkgroupUser(_Workgroup_key)
;
-- 
-- INDEX: Ref184590 
--

CREATE INDEX Ref184590 ON WorkgroupUser(_User_key)
;
-- 
-- INDEX: Ref44 
--

CREATE INDEX Ref44 ON WorkgroupUserFunctionalArea(_FunctionalArea_key)
;
-- 
-- INDEX: Ref35 
--

CREATE INDEX Ref35 ON WorkgroupUserFunctionalArea(_WorkgroupUser_key)
;
-- 
-- INDEX: Ref187595 
--

CREATE INDEX Ref187595 ON WorkgroupUserFunctionalArea(_WorkgroupUser_key)
;
-- 
-- INDEX: Ref213596 
--

CREATE INDEX Ref213596 ON WorkgroupUserFunctionalArea(_FunctionalArea_key)
;
-- 
-- TABLE: User 
--

ALTER TABLE User ADD CONSTRAINT RefWorkgroup592 
    FOREIGN KEY (_DefaultWorkgroup_key)
    REFERENCES Workgroup(_Workgroup_key)
;


-- 
-- TABLE: WorkgroupUser 
--

ALTER TABLE WorkgroupUser ADD CONSTRAINT RefWorkgroup295 
    FOREIGN KEY (_Workgroup_key)
    REFERENCES Workgroup(_Workgroup_key)
;

ALTER TABLE WorkgroupUser ADD CONSTRAINT RefUser590 
    FOREIGN KEY (_User_key)
    REFERENCES User(_User_key)
;


-- 
-- TABLE: WorkgroupUserFunctionalArea 
--

ALTER TABLE WorkgroupUserFunctionalArea ADD CONSTRAINT RefWorkgroupUser595 
    FOREIGN KEY (_WorkgroupUser_key)
    REFERENCES WorkgroupUser(_WorkgroupUser_key)
;

ALTER TABLE WorkgroupUserFunctionalArea ADD CONSTRAINT RefFunctionalArea596 
    FOREIGN KEY (_FunctionalArea_key)
    REFERENCES FunctionalArea(_FunctionalArea_key)
;


