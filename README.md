# amq-practice
amq学习用，在本地启动amq后，本工程里面的用例会带你了解amq的p2p模式、pub-sub模式、pub-dsub模式、queue-producer和queue-consumer模式以及持久化非持久化的区别。


# 常见的几种持久化以及非持久化策略的后果
可靠性及因素

producer的PERSISTENT + queue receiver/durable subscriber
服务器的宕机不会造成消息丢失、先后启动无关系、性能下降比较厉害。

producer的PERSISTENT + non-durable subscriber
服务器的宕机会造成消息丢失、同时先启动producer 发送消息时、再启动consumer，consumer也不会得到新的消息。

producer的NON_PERSISTENT queue receiver/durable subscriber
服务器的宕机会造成消息丢失，先启动producer发送消息不会影响consumer的消息接收,测试该例子可以在测试时候关掉broker

producer的NON_PERSISTENT +non-durable subscriber
服务器的宕机会造成消息丢失、同时先启动producer 发送消息时、再启动consumer，consumer也不会得到新的消息。
