package com.ecnu.hmg.hmggoodshopservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

/**
 * 虚拟主机：一个虚拟主机持有一组交换机、队列和绑定。
 * 虚拟主机的作用在于进行权限管控，rabbitmq默认有一个虚拟主机"/"。
 *
 * 交换机: exchange的功能是用于消息分发，它负责接收消息并转发到与之绑定的队列，
 * exchange不存储消息，如果一个exchange没有binding任何Queue，
 * 那么当它会丢弃生产者发送过来的消息，在启用ACK机制后，如果exchange找不到队列，
 * 则会返回错误。一个exchange可以和多个Queue进行绑定。
 * 4种交换机：
 * 1 路由模式(Direct)
 * direct 类型的行为是"先匹配, 再投送"。即在绑定时设定一个 routing_key,
 * 消息的routing_key 匹配时, 才会被交换器投送到绑定的队列中去。
 * direct是rabbitmq的默认交换机类型。
 * 2 通配符模式(Topic)：
 * 类似路由模式，但是routing_key支持模糊匹配，按规则转发消息（最灵活）。
 * 符号“#”匹配一个或多个词，符号“*”匹配不多不少一个词。
 * 3 发布订阅模式(Fanout)：
 * 转发消息到所有绑定队列（广播），忽略routing_key。
 * 4 Headers:
 * 设置header attribute参数类型的交换机。相较于 direct 和 topic 固定地使用 routing_key ,
 * headers 则是一个自定义匹配规则的类型，忽略routing_key。在队列与交换器绑定时, 会设定一组键值对规则,
 * 消息中也包括一组键值对( headers 属性), 当这些键值对有一对, 或全部匹配时, 消息被投送到对应队列。
 * 在绑定Queue与Exchange时指定一组键值对，当消息发送到RabbitMQ时会取到该消息的headers与Exchange绑定时
 * 指定的键值对进行匹配。如果完全匹配则消息会路由到该队列，否则不会路由到该队列。
 * headers属性是一个键值对，可以是Hashtable，键值对的值可以是任何类型。
 * 匹配规则x-match有下列两种类型：
 * x-match = all ：表示所有的键值对都匹配才能接受到消息
 * x-match = any ：表示只要有键值对匹配就能接受到消息
 */
@Configuration
public class RabbitMQConfig {
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    /**
     * 声明ConnectionFactory
     */
    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory=new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory());
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                if(b){
                    System.out.println("消息成功消费");
                }else{
                    System.out.println("消息消费失败："+s);
                }
            }
        });
        return rabbitTemplate;
    }

    /**
     * 声明队列
     */
    @Bean
    public Queue queue(){
        return new Queue("queue", false, false, false);
    }

    /**
     * 声明topic交换机
     */
    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange("topic-exchange");
    }

    /**
     * 将队列和交换机进行绑定，并指定路由键
     */
    @Bean
    Binding topicBinding(Queue queue, TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with("mq.#");
    }

    /**
     * 声明fanout交换机
     */
    @Bean
    FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanout-exchange");
    }

    /**
     * 将队列与fanout交换机进行绑定
     */
    @Bean
    Binding fanoutBinding(Queue queue, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    /**
     * 声明Headers交换机
     */
    @Bean
    HeadersExchange headersExchange(){
        return new HeadersExchange("headers-exchange");
    }

    /**
     * 将队列和headers交换机进行绑定
     */
    @Bean
    Binding headersBinding(Queue queue, HeadersExchange headersExchange){
        Map<String, Object> map=new HashMap<>();
        map.put("first", "M");
        map.put("second", "Q");
        return BindingBuilder.bind(queue).to(headersExchange).whereAny(map).match();
        //或whereAll(map).match()
    }

}
