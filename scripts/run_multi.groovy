import grails.async.Promise
import static grails.async.Promises.task
import static grails.async.Promises.waitAll

def log = logger('hapnin.scripts.runMulti')

def feed = feed('test concurrent')

List<Promise> promiseList = []
(1..20).each { i ->
    Promise p = task {
        executeFeed(feed)
    }
    p.onError { Throwable e ->
        log.error e
    }
    promiseList << p
    Thread.sleep(5000)
}
waitAll(promiseList)