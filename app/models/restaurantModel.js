module.exports = function(mongoose){
  var Schema = mongoose.Schema;
  var restaurantSchema =  new Schema({
    title: {type: String, required: true, unique: true},
    address: {type: String, required: true},
    about: {type: String},
    phone: {type: String, required: true},
    createdDate: {type: Date, default: Date.now},
    modifiedDate: {type: Date, default:Date.now},
    _hours: {type: Schema.Types.ObjectId, ref: 'Hours'},
    _menu: {type: Schema.Types.ObjectId, ref: 'Menu' }
  });
  
  restaurantSchema.pre('save',function(next){
    var currentDate = new Date().now;
    this.modifiedDate = currentDate;
    if(!this.createdDate){
      this.createDate = currentDate;
    }
    next();
  });
  
  var Restaurant = mongoose.model('Restaurant', restaurantSchema, 'restaurants');
  
  return {
    Restaurant: Restaurant
  };
};