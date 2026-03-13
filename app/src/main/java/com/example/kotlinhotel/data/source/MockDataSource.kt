package com.example.kotlinhotel.data.source

import android.content.Context
import com.example.kotlinhotel.domain.model.*
import io.github.serpro69.kfaker.Faker
import org.json.JSONArray

class MockDataSource(private val context: Context) : HotelDataSource {

    private val faker = Faker()

    private val rooms: List<Room> = listOf(
        Room(1, "101", "Стандарт", 1, 4500.0, "Уютный стандартный номер с видом на внутренний двор.", listOf("Wi-Fi", "ТВ", "Кондиционер"), 2),
        Room(2, "205", "Делюкс", 2, 7800.0, "Просторный номер с панорамными окнами и мини-баром.", listOf("Wi-Fi", "ТВ", "Мини-бар", "Джакузи"), 2),
        Room(3, "312", "Люкс", 3, 14000.0, "Двухкомнатный люкс с гостиной и видом на город.", listOf("Wi-Fi", "ТВ", "Мини-бар", "Джакузи", "Кухня"), 3),
        Room(4, "118", "Стандарт", 1, 4200.0, "Комфортный номер для деловых поездок.", listOf("Wi-Fi", "ТВ", "Рабочий стол"), 1),
        Room(5, "401", "Президентский люкс", 4, 32000.0, "Эксклюзивный двухуровневый люкс с террасой.", listOf("Wi-Fi", "ТВ", "Мини-бар", "Джакузи", "Терраса", "Персональный дворецкий"), 4),
        Room(6, "220", "Делюкс", 2, 8200.0, "Номер с балконом и видом на бассейн.", listOf("Wi-Fi", "ТВ", "Мини-бар", "Балкон"), 2),
        Room(7, "307", "Семейный", 3, 11000.0, "Просторный семейный номер с двумя спальнями.", listOf("Wi-Fi", "ТВ", "Кухня", "Диван"), 4),
        Room(8, "115", "Стандарт", 1, 4800.0, "Стандартный номер с улучшенным видом.", listOf("Wi-Fi", "ТВ", "Сейф"), 2),
        Room(9, "510", "Пентхаус", 5, 45000.0, "Роскошный пентхаус с видом 360° на город.", listOf("Wi-Fi", "ТВ", "Мини-бар", "Джакузи", "Терраса", "Бассейн"), 6),
        Room(10, "214", "Делюкс", 2, 7500.0, "Элегантный делюкс-номер в классическом стиле.", listOf("Wi-Fi", "ТВ", "Мини-бар"), 2)
    )

    private val services: List<HotelService> = listOf(
        // SPA
        HotelService(1, "Классический массаж", "Расслабляющий массаж всего тела, 60 мин.", 3500.0, ServiceCategory.SPA, "💆"),
        HotelService(2, "Ароматерапия", "Массаж с эфирными маслами, 90 мин.", 4800.0, ServiceCategory.SPA, "🌸"),
        HotelService(3, "Спа-пакет «Релакс»", "Массаж + сауна + бассейн, 3 часа.", 8500.0, ServiceCategory.SPA, "🧖"),
        HotelService(4, "Фитнес (день)", "Посещение фитнес-центра на 1 день.", 800.0, ServiceCategory.SPA, "💪"),
        // FOOD
        HotelService(5, "Завтрак в номер", "Континентальный завтрак с доставкой в номер.", 1200.0, ServiceCategory.FOOD, "🍳"),
        HotelService(6, "Ужин в ресторане", "Сет из 3 блюд в ресторане отеля.", 3800.0, ServiceCategory.FOOD, "🍽️"),
        HotelService(7, "Бизнес-ланч", "Обед из 2 блюд + напиток.", 900.0, ServiceCategory.FOOD, "🥗"),
        HotelService(8, "Романтический ужин", "Ужин при свечах на двоих с шампанским.", 9500.0, ServiceCategory.FOOD, "🕯️"),
        // TRANSFER
        HotelService(9, "Трансфер из аэропорта", "Встреча и доставка до отеля, бизнес-класс.", 3200.0, ServiceCategory.TRANSFER, "🚗"),
        HotelService(10, "Трансфер в аэропорт", "Доставка из отеля в аэропорт.", 3200.0, ServiceCategory.TRANSFER, "✈️"),
        HotelService(11, "Аренда авто (день)", "Автомобиль с водителем на целый день.", 12000.0, ServiceCategory.TRANSFER, "🚘"),
        HotelService(12, "Автобус (шаттл)", "Шаттл до центра города (расписание).", 400.0, ServiceCategory.TRANSFER, "🚌"),
        // EXCURSION
        HotelService(13, "Обзорная экскурсия", "Тур по главным достопримечательностям, 4 ч.", 2500.0, ServiceCategory.EXCURSION, "🏛️"),
        HotelService(14, "Ночная экскурсия", "Вечерний тур с ужином, 3 часа.", 4200.0, ServiceCategory.EXCURSION, "🌆"),
        HotelService(15, "Велоэкскурсия", "Тур на велосипедах по паркам, 2 ч.", 1800.0, ServiceCategory.EXCURSION, "🚴"),
        // CLEANING
        HotelService(16, "Уборка номера (вне расписания)", "Внеплановая уборка по запросу.", 600.0, ServiceCategory.CLEANING, "🧹"),
        HotelService(17, "Глубокая уборка", "Генеральная уборка номера.", 1500.0, ServiceCategory.CLEANING, "✨"),
        // LAUNDRY
        HotelService(18, "Стирка (до 5 кг)", "Стирка и глажка одежды, 24 ч.", 900.0, ServiceCategory.LAUNDRY, "👔"),
        HotelService(19, "Экспресс-стирка", "Стирка и глажка за 4 часа.", 1600.0, ServiceCategory.LAUNDRY, "⚡"),
        HotelService(20, "Химчистка", "Профессиональная чистка костюмов и верхней одежды.", 2500.0, ServiceCategory.LAUNDRY, "🧥")
    )

    private val _reviews: MutableList<Review> = mutableListOf(
        Review(1, "Александр Петров", 5.0f, "Отличный отель! Персонал очень внимательный, номер чистый и уютный. Обязательно вернёмся.", "12.03.2025"),
        Review(2, "Мария Иванова", 4.5f, "Прекрасное место для отдыха. Завтраки просто великолепны. Немного шумновато по вечерам.", "08.03.2025"),
        Review(3, "Дмитрий Сидоров", 5.0f, "Лучший отель в котором я когда-либо останавливался. Спа-центр на высшем уровне!", "01.03.2025"),
        Review(4, "Елена Козлова", 4.0f, "Хороший отель по разумной цене. Расположение удобное. Рекомендую номера Делюкс.", "25.02.2025"),
        Review(5, "Сергей Новиков", 3.5f, "Неплохо, но есть над чем работать. Долго ждали заселения. Вид из номера хороший.", "18.02.2025"),
        Review(6, "Анна Белова", 5.0f, "Невероятная атмосфера! Персонал очень дружелюбный. Ресторан — отдельный повод приехать.", "10.02.2025"),
        Review(7, "Игорь Морозов", 4.5f, "Отличный сервис, всё на уровне. Небольшие нюансы с парковкой.", "03.02.2025"),
        Review(8, "Наталья Федорова", 5.0f, "Провели медовый месяц — это было волшебно! Спасибо за романтический ужин.", "28.01.2025"),
        Review(9, "Павел Орлов", 4.0f, "Хороший отель для командировок. Быстрый Wi-Fi, удобная кровать, всё что нужно.", "20.01.2025"),
        Review(10, "Юлия Соколова", 4.5f, "Очень понравилось! Чистота, комфорт, вкусная еда. Советую брать пакет со спа.", "15.01.2025"),
        Review(11, "Виктор Попов", 3.0f, "Номер соответствует описанию. Хотелось бы более оперативного реагирования на запросы.", "08.01.2025"),
        Review(12, "Ольга Лебедева", 5.0f, "Прекрасный отдых! Дети в восторге от бассейна, мы — от спа. Всё по высшему разряду.", "02.01.2025"),
        Review(13, "Андрей Козин", 4.5f, "Цена полностью оправдана. Красивый интерьер, профессиональный персонал.", "26.12.2024"),
        Review(14, "Татьяна Миронова", 4.0f, "Хорошее расположение, рядом с достопримечательностями. Завтрак мог быть разнообразнее.", "19.12.2024"),
        Review(15, "Константин Волков", 5.0f, "Великолепный отель! Трансфер от аэропорта прошёл идеально. Рекомендую всем!", "12.12.2024")
    )

    private val currentUser = User(
        id = 1,
        name = "Родион Смирнов",
        email = "rodion@example.com",
        phone = "+7 (999) 123-45-67",
        bookingHistory = listOf(
            Booking(1, "205", "Делюкс", 2, "15.03.2025", "20.03.2025", "Активное", "11:00")
        )
    )

    override suspend fun getRooms(): List<Room> = rooms

    override suspend fun getServices(): List<HotelService> = services

    override suspend fun getReviews(): List<Review> = _reviews.toList()

    override suspend fun getEvents(): List<Event> {
        return try {
            val json = context.assets.open("events.json").bufferedReader().readText()
            val array = JSONArray(json)
            (0 until array.length()).map { i ->
                val obj = array.getJSONObject(i)
                Event(
                    id = obj.getInt("id"),
                    title = obj.getString("title"),
                    time = obj.getString("time"),
                    location = obj.getString("location")
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getCurrentUser(): User = currentUser

    override fun addReview(review: Review) {
        _reviews.add(0, review)
    }

    override fun addBooking(booking: Booking) {
        // In-memory update would require mutable user, simplified for mock
    }
}
