# ticker-news

This is a web application that gives you current news articles given a stock ticker symbol. I created it to play around with
[Compojure][1], [Hiccup][2] and [Bootstrap][3]. News is currently powered by [Yahoo Company News RSS Feed][4] but the application
can be easily extended to add more sources.

This application can be easily deployed to [Amazon Web Services][5], please see [Lein-Beanstalk][6] for deployment steps.

## Prerequisites

You will need [Leiningen][7] 1.7.0 or above installed.

[1]: https://github.com/weavejester/compojure
[2]: https://github.com/weavejester/hiccup
[3]: https://github.com/twbs/bootstrap
[4]: http://developer.yahoo.com/finance/company.html
[5]: http://aws.amazon.com
[6]: https://github.com/weavejester/lein-beanstalk
[7]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## License

Copyright © 2013 Saurabh Pathak