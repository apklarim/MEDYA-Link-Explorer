package com.medya.linkexplorer.search

class SourceDiscoveryEngine(
    private val sourceManager: SearchSourceManager = SearchSourceManager()
) {

    init {
        loadDefaultSources()
    }

    /**
     * Varsayılan kaynakları yükler.
     * Şimdilik boş geliyor.
     * İleride Ayarlar ekranından kullanıcı kendi kaynaklarını ekleyecek.
     */
    private fun loadDefaultSources() {
        // Varsayılan kaynaklar burada yüklenecek.
    }

    /**
     * Taramaya uygun kaynakları döndürür.
     */
    fun getSources(): List<SearchSource> {
        return sourceManager.getEnabled()
    }

    /**
     * Yeni kaynak ekler.
     */
    fun addSource(
        name: String,
        url: String,
        priority: Int = 0
    ) {

        sourceManager.add(
            SearchSource(
                name = name,
                url = url,
                priority = priority
            )
        )
    }

    /**
     * Kaynağı siler.
     */
    fun removeSource(url: String) {
        sourceManager.remove(url)
    }

    /**
     * Tüm kaynakları temizler.
     */
    fun clear() {
        sourceManager.clear()
    }
}
