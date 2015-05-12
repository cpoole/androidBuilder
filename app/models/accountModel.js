module.exports = function(mongoose){
    var Schema = mongoose.Schema;
    var accountSchema = new Schema({
      _users:[{type: Schema.Types.ObjectId, ref: 'User'}],
      _restaurants:[{type: Schema.Types.ObjectId, ref: 'Restaurant'}],
      _admin:{type: Schema.Types.ObjectId, ref: 'User'}
    });
    var Account = mongoose.model('Account', accountSchema);
    return {
      Account:Account
    };
};