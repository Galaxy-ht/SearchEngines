# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface
from itemadapter import ItemAdapter
import pymysql


class NewsPipeline:
    conn = pymysql.connect(host='localhost', port=3306, user='root', password='642380', db='test')
    cur = conn.cursor()

    def process_item(self, item, spider):
        # self.saveData(item)
        sql = "insert into news_detail(title,content,url) values (%s, %s, %s)"
        self.cur.execute(sql, (item['news_title'], item['news_content'], item['news_detail_url']))
        self.conn.commit()
        print(item)
        return item

    def saveData(item):

        try:
            conn = pymysql.connect(host='localhost', port=3306, user='root', password='642380', db='test')
            cur = conn.cursor()
            sql = "insert into news_detail(title,content,url) values (%s, %s, %s)"
            cur.execute(sql, (item['news_title'], item['news_content'], item['news_detail_url']))
            # cur.executemany("INSERT INTO Student(Num, Name, Class, Gender,score) VALUES (?, ?, ?, ?, ?)", stu_datas)
            conn.commit()
        except Exception as e:
            print(repr(e))
            print(conn.Error)
            print(conn.DatabaseError)
            # 事务回滚
            conn.rollback()
        finally:
            cur.close()
            conn.close()
            cur = None
            conn = None
