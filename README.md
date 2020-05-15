# API-fetch-with-added-features
This app uses REST API - Retrofit library to fetch data from the API and is integrated with Firebase Realtime Database along with added touch controls.

Api = http://kekizadmin.com/kekiz_api/actions.php?action=get_cakes&category=27
This Api gives a list of cakes with their details.
For the images, Glide library has been used: Img url = http://kekizadmin.com/uploads/catrgories/
ex : http://kekizadmin.com/uploads/catrgories/2e59f0e59512c6554952d566ff2163b3.jpg

1) first api call gets the cake list: 
image name = pictures ( from : data > w_l_p > pictures)
cake name = cake_name ( from : data > cake_name)
cake weight = weight ( from : data > w_l_p > weight) 
cake pricce = price ( from : data > w_l_p > price) 

2) Add to cart screen has two lists:-> cart list and selected cakes list

3) can perform 2 operations on cart list item:-> move in selected cake ( using button click and touch to move) and item delete option

4) can perform 2 operations on selected cake list item:-> move up and down (using touch to move up and down) and item delete option

For touch controls, ItemTouchHelper class has been used.

<img src="https://user-images.githubusercontent.com/49456940/82032100-6623ea00-96b8-11ea-89a0-22c2934f3c0d.jpeg" height="300"> <img src="https://user-images.githubusercontent.com/49456940/82032123-6e7c2500-96b8-11ea-952c-43ee384c2ed1.jpeg" height="300"> <img src="https://user-images.githubusercontent.com/49456940/82032135-73d96f80-96b8-11ea-90bf-1130b62a8fbc.jpeg" height="300">
