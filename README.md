简书用户动态爬取系统：
ssm+jsoup+spark+redis+mysql
MyEclipse2014

介绍：
通过简书用户的用户编号，爬取用户全部动态信息，比如：注册时间、第一次关注的用户、第一次发表的文章、第一次关注的专题等......通过sparksql计算，进行用户动态的分析，比如：每天以小时为单位的动态折线图、各个动态所占百分比、月度动态数目展示等，利用echars图表展示结果。此项目将spring与spark2.2.1进行整合，经sparksql执行的计算结果作为热数据保存在redis中。

