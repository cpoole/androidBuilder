module.exports = function(mongoose){
  var Schema = mongoose.Schema;
  var hoursSchema = new Schema({
    monO: Number,
    monC: Number,
    tueO: Number,
    tueC: Number,
    wedO: Number,
    wedC: Number,
    thuO: Number,
    thuC: Number,
    friO: Number,
    friC: Number,
    satO: Number,
    satC: Number,
    sunO: Number,
    sunC: Number,
    _parent:{type:Schema.Types.ObjectId, ref:'Restaurant'}
  });

  var Hours = mongoose.model('Hours', hoursSchema, 'hours');
  
  return{
    Hours:Hours
  };
};