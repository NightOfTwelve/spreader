db.content.ensureIndex({id:1},{name:"pk_content_id",unique:true});
db.content.ensureIndex({type:1,websiteId:1,websiteUid:1,entry:1},{name:"uk_content_url",unique:true});
db.content.ensureIndex({type:1,websiteId:1,websiteContentId:1},{name:"uk_content_website_content",unique:true});
db.content.ensureIndex({uid:1},{name:"idx_content_uid"});
