DROP PROCEDURE IF EXISTS dropColumnIfExists
ZZZ


CREATE PROCEDURE dropColumnIfExists(
    tname VARCHAR(64),
    cname VARCHAR(64)
)
BEGIN
   SET @COUNT = (SELECT COUNT(*)
                FROM `INFORMATION_SCHEMA`.`COLUMNS`
                WHERE `TABLE_SCHEMA` = SCHEMA()
                      AND `TABLE_NAME` = tname
                      AND `COLUMN_NAME` = cname);
    IF @COUNT > 0
    THEN
         SET @dropColumnIfExists = CONCAT('ALTER TABLE `', tname, '` DROP COLUMN `', cname, '`');
        PREPARE drop_query FROM @dropColumnIfExists;
        EXECUTE drop_query;
    END IF;
END
ZZZ



DROP PROCEDURE IF EXISTS dropIndexIfExists
ZZZ

CREATE PROCEDURE dropIndexIfExists(IN thetable varchar(128),IN theindexname varchar(128) )
BEGIN
  IF ((SELECT count(*) AS index_exists
          FROM   information_schema.STATISTICS
        WHERE  table_schema = DATABASE()
          AND    table_name = thetable
          AND    index_name = theindexname) > 0) THEN
    SET @s = concat('DROP INDEX ' , theindexname , ' ON ' , thetable);
    prepare stmt FROM @s;
    execute stmt;
  END IF;
END
ZZZ