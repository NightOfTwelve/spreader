db.reply.ensureIndex({id:1},{name:"pk_reply_id",unique:true});
db.reply.ensureIndex({content:1},{name:"uk_reply_content",unique:true});