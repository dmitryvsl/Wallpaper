package com.example.wallpaper.feature.category_detail.data

import com.example.wallpaper.core.model.CategoryType
import com.example.wallpaper.core.model.DataState
import com.example.wallpaper.feature.category_detail.domain.model.Image
import com.example.wallpaper.feature.category_detail.domain.repository.ImageRepository
import javax.inject.Inject

class FakeImageRepository @Inject constructor() : ImageRepository {

    override fun fetchImages(categoryType: CategoryType): DataState<List<Image>> {
        val randomNumber = (0..10).random()
        // 33% chance return error
        return if (randomNumber % 3 == 0) DataState.Error(UnknownError())
        else DataState.Success(images)
    }

    private val images = listOf(
        Image(
            url = "https://pixabay.com/get/gc73225fe117f1bb6cbca2fabb921d82879f2c2e318e773e91e9802dd862289d5a3b565e33f90a437dc4330b3956afd36cc8ff2e24b7ae76c5d252ca04bc23818_640.jpg",
            fullImageUrl = "https://pixabay.com/get/g34c00c557ccb5f3c17a78612f69d5571110f361356b923fd82aeeaca351f43776bd3b89ef3703518342459719201c6f98d96cf304c655ddb7c28bad246fb173f_1280.jpg"
        ),
        Image(
            url = "https://pixabay.com/get/g51eaf1b9de52e634eac4bf603f72f68c9a923271cbaa11fa2d06746f67f93d9a966e5407958cf8eec32fa41b40c1451d9ba05655041424746124410d9b5a45f5_640.jpg",
            fullImageUrl = "https://pixabay.com/get/g5432eb315a8ec13d92c5cd4de0a33c7867530c88aa2ab6282fe1b690198d57c57a7acd38d387aad04c08b1939f737501694bd5eed5cfea986e6596f7e14bace5_1280.jpg"
        ),
        Image(
            url = "https://pixabay.com/get/ga58c9f8984413c66ef94ef38181f7f2327dd457c84d355405a69e215474978972b3e4928f42d5ad3ad10b32e37f90c5ac2d0fd60e36a360fe042feeb6ade6caf_640.jpg",
            fullImageUrl = "https://pixabay.com/get/g45f3ff6847dab5a55663f16b892025cf03235cc8d85a3c5387f78d5cc0b4da5e7fb338fe050f52c2614483cac58583009d49533c680830ec5ff7fc9e0984a4e5_1280.jpg"
        ),
        Image(
            url = "https://pixabay.com/get/g7a7a7531003b6b3a2ea0005b99c92e54fbd09ea1e45deb7723c13c742e1e90fd7c64a7f00092718706b209993284b19bbf37bda88ff742eb27bc690b6ffea488_640.jpg",
            fullImageUrl = "https://pixabay.com/get/gb4e6a49d7a9339433e3b789ddc9ca80f5757c1fbeb797e8c12db815eccf59eaf6966ac47a3bc5bf1ebd1202d8a4750bb35db020d6e43e1cb51d27b125a88a89b_1280.jpg"
        ),
        Image(
            url = "https://pixabay.com/get/g5b2ec324726c39b7a5068770772ce45bb0fec58033599ca5543152480215a0b7668d50fdbf1e90783a63aa180fae2f8b00fde00bbc5af650dd8c45dc0b4cbf8a_640.png",
            fullImageUrl = "https://pixabay.com/get/g58c77f61181c8203b4ee9e260b4e522f62324727cd9418a11df6536a808ed07509da8bdd7ceaf201ee16381bc93791a6a890a54c9d7d60a0ae03933938ff2bb7_1280.png"
        ),
        Image(
            url = "https://pixabay.com/get/g08f73714716f8d6c20385d2f589571cc76899601d633a17f55a983edbe863263381521ba41116bb040402ed1755bf26e0a5210644759b6ba58f40c607578b91f_640.jpg",
            fullImageUrl = "https://pixabay.com/get/gb908f4df42e39dfe4e02ce6dab35475517ff3ec09a968005c56dd79b83cffe45c1fab74a02eba7f263165959812a41f2149475edd3b2d2fd0c71ec325542bb8c_1280.jpg"
        ),
        Image(
            url = "https://pixabay.com/get/g845065f0190dd62fb3c454da038a7ab0febe3ea79d25b0c37464f0feed42d614e34f21bfdfcc6e3d002f3a3b856aeb82d18dbf335fc3f7f56517a003c689ffc4_640.jpg",
            fullImageUrl = "https://pixabay.com/get/ga3cdc0cfd708f524a6ac696b5adc2feacb4bcff0b98c9d140c63f1c385297bb947d3996d46042816aa9c8314e049ccc96fb62654efa0afda3bb4ab1fdfcf9247_1280.jpg"
        ),
        Image(
            url = "https://pixabay.com/get/gd302c77b84431287c070faec146594ddfe4b801543946de6026d185bad280d861bb059a594a9e95a45f9c06e4e01b8e84f432cbcae894209b02a997c5af80ca9_640.jpg",
            fullImageUrl = "https://pixabay.com/get/g93e276eb8d4906ba03ba1efeeeb3cfe754b1ddcb1b76a5ca5b0d0a21def2e1080b9f23ba6e0a8648e75d648be78f23b174074628fdfda519497770229db739d6_1280.jpg"
        ),
        Image(
            url = "https://pixabay.com/get/g80363bc69c1d9df936703a73b6c3968f471bbe0c5d1d4638a872709f426a2afae8e09d25a1a63e488beda64ae2092bee6e1a7d5bc2302fd697a82a4362d7d228_640.jpg",
            fullImageUrl = "https://pixabay.com/get/g78a8ae73fd24240d744633896ffba4c38d414d1b2694ed37a1a2f946445e371c06066f5af267f7d962df77268ad28f327c23e6531afd4de1aa3a4d1f5452a3fd_1280.jpg"
        ),
    )
}