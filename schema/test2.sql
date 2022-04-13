SELECT 'hipis' AS t, COUNT(1) FROM hipis UNION ALL
SELECT 'monitor' AS t, COUNT(1) FROM monitor UNION ALL
SELECT 'data_value' AS t, COUNT(1) FROM data_value UNION ALL
SELECT 'trend' AS t, COUNT(1) FROM trend UNION ALL
SELECT 'work_performance' AS t, COUNT(1) FROM work_performance UNION ALL
SELECT 'mes_file' AS t, COUNT(1) FROM mes_file UNION ALL
SELECT 'file_completed' AS t, COUNT(1) FROM file_completed UNION ALL
SELECT 'work_status' AS t, COUNT(1) FROM work_status ;