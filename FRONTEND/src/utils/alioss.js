//阿里oss存储图片
import OSS from 'ali-oss';

// var OSS = require('ali-oss').Wrapper
export function client() {
  var client = new OSS({
    region: 'oss-cn-shanghai',
    accessKeyId: 'LTAITofzv8ygln3y',
    accessKeySecret: 'XXnt5uPjVn2M9Olt2kAZzdcJ3cvppF',
    bucket: 'sjzhistory'
  })  //后端提供数据
  return client
}
