BEGIN;

INSERT INTO tb_tenant (ID, NAME, TYPE, DESCRIPTION, COUNTRY_ID, HOST_URL, reg_datetime, mod_datetime) VALUES( 'emoldino-own-kr', 'eMoldino', 0, 'eMoldino Members', 'KR', 'https://serentity.emoldino.com', NOW(), NOW());
COMMIT;