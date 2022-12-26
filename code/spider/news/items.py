# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class NewsItem(scrapy.Item):
    # define the fields for your item here like:
    news_title = scrapy.Field()
    news_content = scrapy.Field()
    news_detail_url = scrapy.Field()
    
