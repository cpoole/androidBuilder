module.exports = function(mongoose){
  var Schema = mongoose.Schema;
  var menuSchema = new Schema({
    _parent:{type:Schema.Types.ObjectId, ref:'Restaurant'},
    modified: {type: Date, required:true},
    Categories:[
      {
        title: String,
        imageUrl: String,
        entries:[
          {
            title:String,
            description: String,
            price:Number,
            imageUrl:String
          }
        ]
      }   
    ] 
  });
  var Menu = mongoose.model('Menu', menuSchema, 'menus');
  return{
    Menu:Menu
  };
};