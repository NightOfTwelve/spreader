db.reply.ensureIndex({id:1},{name:"pk_reply_id",unique:true});
db.reply.ensureIndex({content:1},{name:"uk_reply_content",unique:true});
db.reply.ensureIndex({isIndex:1},{name:"idx_reply_is_index"});
db.reply.update({"reReplyCount":0},{$set:{"isIndex":false}},false,true);