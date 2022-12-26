import scrapy
import logging
from selenium import webdriver
from news.items import NewsItem

logger = logging.getLogger(__name__)


class WyNewsSpider(scrapy.Spider):
    name = 'wy_news'
    allowed_domains = ['news.163.com']
    start_urls = ['https://news.163.com/']
    models_urls = []  # 存储五个版块对应详情的url

    # 实例化一个浏览器对象
    def __init__(self, **kwargs):
        # self.s = Service(r"H:\\数据采集与分析\\scrapy_code\\mynews\\chromedriver.exe")
        # self.driver = webdriver.Chrome(service=self.s)
        super().__init__(**kwargs)
        options = webdriver.ChromeOptions()
        options.add_experimental_option('excludeSwitches', ['enable-logging'])
        self.driver = webdriver.Chrome(executable_path="H:\数据采集与分析\scrapy_code\mynews\chromedriver.exe",
                                       options=options)

    def parse(self, response):
        # divs = response.xpath('//*[@id="index2016_wrap"]/div[3]/div[2]/div[3]/div[2]/div[5]/div/ul/li[1]/div[2]/div')
        # for div in divs:
        #     url = div.xpath('./a/@href').extract_first()
        li_list = response.xpath('//*[@id="index2016_wrap"]/div[3]/div[2]/div[2]/div[2]/div/ul/li')
        alist = [1]
        for index in alist:
            model_url = li_list[index].xpath('./a/@href').extract_first()
            self.models_urls.append(model_url)

            # 依次对各个板块url发起请求
        for url in self.models_urls:
            yield scrapy.Request(url, callback=self.parse_model)

    def parse_model(self, response):  # 解析每一个版块页面中对应新闻的标题和新闻详情的url
        div_list = response.xpath('/html/body/div/div[3]/div[3]/div[1]/div[1]/div/ul/li/div/div')
        # div_list = response.xpath('/html/body/div/div[3]/div[4]/div[1]/div[1]/div/ul/li/div/div')
        for div in div_list:
            news_title = div.xpath('./div/div[1]/h3/a/text() | ./div/h3/a/text()').extract_first()
            news_detail_url = div.xpath('./div/div[1]/h3/a/@href | ./div/h3/a/@href').extract_first()

            item = NewsItem()
            item['news_title'] = news_title
            item['news_detail_url'] = news_detail_url
            # 对新闻详情页的url发起请求
            yield scrapy.Request(url=news_detail_url, callback=self.parse_detail, dont_filter=True, meta={'item': item})

    def parse_detail(self, response):  # 解析新闻内容
        news_content = response.xpath(
            '//*[@id="content"]/div[2]//text() | //*[@id="content"]/div[2]/div[1]//text()').extract()
        news_content = ''.join(news_content)  # 列表转字符串
        item = response.meta['item']
        item['news_content'] = news_content
        yield item

        # 关闭浏览器驱动程序

    def closed(self, spider):
        self.driver.quit()
