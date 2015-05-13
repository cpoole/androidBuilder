module.exports = function(mongoose){
 
  var bcrypt = require('bcrypt');
  var Schema = mongoose.Schema;
  
  //mongoose.connect('mongodb://localhost/androidBuilder');
  
  var userSchema = new Schema({
    local          : {
      email        : {type: String, required: true, unique: true},
      password     : {type: String, required: true},
    },
    facebook       : {
      id           : String,
      token        : String,
      email        : String,
      name         : String
    },
    twitter        : {
      id           : String,
      token        : String,
      displayName  : String,
      username     : String
    },
    google         : {
      id           : String,
      token        : String,
      email        : String,
      name         : String
    },
    createdDate: {type: Date, default: Date.now},
    updatedDate: {type: Date, default: Date.now},
    lastSignOn: {type: Date, default: Date.now},
    _restaurants: [{type: Schema.Types.ObjectId, ref: 'Restaurant' }]
  });
  
  userSchema.pre('save',function(next){
    var currentDate = new Date().now;
    this.updatedDate = currentDate;
    
    if(!this.createdDate){
      this.createdDate = currentDate;
    }
    
    next();
  });
  
  userSchema.methods.generateHash = function(password){
    var salt = bcrypt.genSaltSync(10);
    return bcrypt.hashSync(password, salt);
  };
  
  userSchema.methods.validatePassword = function(password){
    return bcrypt.compareSync(password, this.local.password);
  };
  
  var User = mongoose.model('User', userSchema, 'users');
  
  return {
    User: User
  };
  
};



