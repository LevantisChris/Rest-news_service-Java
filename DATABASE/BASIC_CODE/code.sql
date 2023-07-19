CREATE TABLE USERS (
	USERNAME VARCHAR(100) PRIMARY KEY,
    PASSWORD VARCHAR(100) NOT NULL,
    NAME VARCHAR(150) NOT NULL,
    SURNAME VARCHAR(150) NOT NULL,
    ROLE_ID INT NOT NULL,
    FOREIGN KEY (ROLE_ID) REFERENCES ROLES (ID)
);

CREATE TABLE NOT_USERS (
	ID INT PRIMARY KEY,
    ROLE_ID INT NOT NULL
);

CREATE TABLE ROLES (
	ID INT PRIMARY KEY,
    DESCRIPTION VARCHAR(200) NOT NULL
);

CREATE TABLE ARTICLES (
	ID INT PRIMARY KEY AUTO_INCREMENT,
    TITLE VARCHAR(250) UNIQUE NOT NULL,
    CONTENT VARCHAR(2500) NOT NULL,
    DATE_CREATION DATE NOT NULL,
    TOPIC_ID INT NOT NULL,
    STATE_ID INT NOT NULL,
    CREATOR_USERNAME VARCHAR(100) NOT NULL,
    FOREIGN KEY (TOPIC_ID) REFERENCES TOPIC (ID),
	FOREIGN KEY (STATE_ID) REFERENCES STATES (ID),
    FOREIGN KEY (CREATOR_USERNAME) REFERENCES USERS (USERNAME)
);

## alert is about, if the article has been declined or not ...
ALTER TABLE ARTICLES
ADD alert BOOLEAN DEFAULT FALSE;

CREATE TABLE ALERTS_CAUSES ( 
	ARTICLE_ID INT PRIMARY KEY, 
    CAUSE VARCHAR(2500) NOT NULL
);

CREATE TABLE COMMENTS (
	ID INT PRIMARY KEY AUTO_INCREMENT,
    CONTENT VARCHAR(500) NOT NULL,
    DATE_CREATION DATE NOT NULL,
    ARTICLE_ID INT NOT NULL,
    STATE_ID INT NOT NULL,
    CREATOR_USERNAME VARCHAR(100), ## IT MIGHT BE A VISITOR SO IT CAN BE NULL ...
    FOREIGN KEY (ARTICLE_ID) REFERENCES ARTICLES (ID),
    FOREIGN KEY (STATE_ID) REFERENCES STATES (ID)
);

CREATE TABLE TOPIC (
	ID INT PRIMARY KEY AUTO_INCREMENT,
    TITLE VARCHAR(250) UNIQUE NOT NULL, ## WE CAN NOT HAVE A TOPIC WITH THE SAME NAME ...
    DATE_CREATION DATE NOT NULL,
    STATE_ID INT NOT NULL,
    CREATOR_USERNAME VARCHAR(100) NOT NULL,
    PARENT_TOPIC_ID INT, ## IT MIGHT NOT HAVE A PARENT NODE ...
    FOREIGN KEY (STATE_ID) REFERENCES STATES (ID),
	FOREIGN KEY (CREATOR_USERNAME) REFERENCES USERS (USERNAME),
    FOREIGN KEY (PARENT_TOPIC_ID) REFERENCES TOPIC (ID)
);

INSERT INTO TOPIC (TITLE, DATE_CREATION, STATE_ID, CREATOR_USERNAME, PARENT_TOPIC_ID)
VALUES ('This is the topic', '2023-06-28', 4, 'DimitraAlexa', NULL);
INSERT INTO TOPIC (TITLE, DATE_CREATION, STATE_ID, CREATOR_USERNAME, PARENT_TOPIC_ID)
VALUES ('Cars', '2023-06-13', 4, 'AlexAlexakis', NULL);
INSERT INTO TOPIC (TITLE, DATE_CREATION, STATE_ID, CREATOR_USERNAME, PARENT_TOPIC_ID)
VALUES ('Motorcycle', '2022-05-25', 4, 'AlexAlexakis', NULL);

## 1) CREATED
## 2) SUBMITTED
## 3) APPROVED
## 4) PUBLISHED
CREATE TABLE STATES (
	ID INT PRIMARY KEY,
    DESCRIPTION VARCHAR(200) NOT NULL
);

#############################################################################################################
#############################################################################################################
#############################################################################################################
#############################################################################################################

## STATES
INSERT INTO STATES(ID, DESCRIPTION) 
VALUES (1, "CREATED");
INSERT INTO STATES(ID, DESCRIPTION) 
VALUES (2, "SUBMITTED");
INSERT INTO STATES(ID, DESCRIPTION) 
VALUES (3, "APPROVED");
INSERT INTO STATES(ID, DESCRIPTION) 
VALUES (4, "PUBLISHED");

## ROLES
INSERT INTO ROLES(ID, DESCRIPTION) 
VALUES (1, "VISITOR");
INSERT INTO ROLES(ID, DESCRIPTION) 
VALUES (2, "JOURNALIST");
INSERT INTO ROLES(ID, DESCRIPTION) 
VALUES (3, "CURATOR");

## USERS
## JOURNALIST
INSERT INTO USERS (USERNAME, PASSWORD, NAME, SURNAME, ROLE_ID)
VALUES ('MariaMaraki', '1234', 'Maria', 'Maraki', 2);
INSERT INTO USERS (USERNAME, PASSWORD, NAME, SURNAME, ROLE_ID)
VALUES ('ChristosCHR', '123456', 'Chistos', 'Christakis', 2);
INSERT INTO USERS (USERNAME, PASSWORD, NAME, SURNAME, ROLE_ID)
VALUES ('GeorgeGEO', '7896', 'Giorgos', 'Giorgakis', 2);
INSERT INTO USERS (USERNAME, PASSWORD, NAME, SURNAME, ROLE_ID)
VALUES ('AlexAlexakis', '456321', 'Alexantros', 'Alexandrakis', 2);
## CURATORS
INSERT INTO USERS (USERNAME, PASSWORD, NAME, SURNAME, ROLE_ID)
VALUES ('ElenaELE', '12369', 'Elena', 'Elenitsa', 3);
INSERT INTO USERS (USERNAME, PASSWORD, NAME, SURNAME, ROLE_ID)
VALUES ('DimitraAlexa', '6987', 'Dimitra', 'Alexa', 3);