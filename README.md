# amq-practice
amq学习用，在本地启动amq后，本工程里面的用例会带你了解amq的p2p模式、pub-sub模式、pub-dsub模式、queue-producer和queue-consumer模式以及持久化非持久化的区别。



可靠性及因素

PERSISTENT

queue receiver/durable subscriber

消费一次且仅消费一次。可靠性最好，但是占用服务器资源比较多。

PERSISTENT

non-durable subscriber

最多消费一次。这是由于non-durable subscriber决定的，如果消费端宕机或其他问题导致与JMS服务器断开连接，等下次再联上JMS服务器时的一系列消息，不为之保留。

NON_PERSISTENT

queue receiver/durable subscriber

最多消费一次。这是由于服务器的宕机会造成消息丢失

NON_PERSISTENT

non-durable subscriber

最多消费一次。这是由于服务器的宕机造成消息丢失，也可能是由于non-durable subscriber的性质所决定
