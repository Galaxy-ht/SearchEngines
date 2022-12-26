# Define here the models for your spider middleware
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/spider-middleware.html

from scrapy import signals
from scrapy.http import HtmlResponse
# useful for handling different item types with a single interface
from itemadapter import is_item, ItemAdapter
from time import sleep


class NewsDownloaderMiddleware:
    # Not all methods need to be defined. If a method is not defined,
    # scrapy acts as if the downloader middleware does not modify the
    # passed objects.

    
    def process_request(self, request, spider):
        # Called for each request that goes through the downloader
        # middleware.

        # Must either:
        # - return None: continue processing this request
        # - or return a Response object
        # - or return a Request object
        # - or raise IgnoreRequest: process_exception() methods of
        #   installed downloader middleware will be called
        return None
    #该方法拦截五大版块对应的响应对象进行篡改
    def process_response(self, request, response, spider):#spider爬虫对象
         # 获取在爬虫类中定义的浏览器对象
        driver = spider.driver

        # 挑选出需要篡改的response对应的request对应的url
        if request.url in spider.models_urls:
            # 针对五大板块对应的response进行篡改
            # 对五大板块的url发起请求
            driver.get(request.url)
            sleep(2)

            # 获取了包含动态加载数据的页面
            page_text = driver.page_source
            # 实例化一个新的响应对象（包含动态加载的数据）来代替原来旧的响应对象
            new_response = HtmlResponse(url=request.url,body=page_text,encoding='utf-8',request=request)
            return new_response
        else:
            # 其它请求的response
            return response

    def process_exception(self, request, exception, spider):
        # Called when a download handler or a process_request()
        # (from other downloader middleware) raises an exception.

        # Must either:
        # - return None: continue processing this exception
        # - return a Response object: stops process_exception() chain
        # - return a Request object: stops process_exception() chain
        pass


