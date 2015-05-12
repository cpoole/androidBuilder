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
  var Restaurant = mongoose.model('Restaurant', restaurantSchema);
  
  return {
    Restaurant: Restaurant
  };
};