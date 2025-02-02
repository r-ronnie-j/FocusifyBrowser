package com.example.myapplication.viewModel

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.database.db
import com.example.myapplication.database.downloads.DownloadEntity
import com.example.myapplication.fetch
import com.tonyodev.fetch2.Download
import com.tonyodev.fetch2.Error
import com.tonyodev.fetch2.FetchListener
import com.tonyodev.fetch2core.DownloadBlock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DownloadViewModel : ViewModel() {
    val downloads = mutableStateListOf<DownloadEntity>()
    val blocks = mutableStateListOf<DownloadBlock>()

    fun pause(id: Int) {
        fetch?.pause(id)
    }

    fun resume(id: Int) {
        fetch?.resume(id)
    }

    fun cancel(id: Int) {
        fetch?.cancel(id)
    }

    init {
        db?.let { db ->
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val allDownloads = db.downloadDao().getAll()
                    withContext(Dispatchers.Main) {
                        downloads.addAll(allDownloads)
                    }
                }
            }
        }
        fetch?.let { fetch ->
            fetch.addListener(object : FetchListener {

                override fun onAdded(download: Download) {
                    val downloadEntity = DownloadEntity.fromDownload(download)
                    downloads.add(0, downloadEntity)
                    db?.let { db ->
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                val downloadDao = db.downloadDao()
                                downloadDao.upsert(downloadEntity)
                            }
                        }
                    }
                }

                override fun onCancelled(download: Download) {
                    val downloadEntity = DownloadEntity.fromDownload(download)
                    val index = downloads.indexOfFirst { it.id == download.id.toLong() }
                    if (index != -1) {
                        downloads[index] = downloadEntity
                    }
                    db?.let { db ->
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                val downloadDao = db.downloadDao()
                                downloadDao.update(downloadEntity)
                            }
                        }
                    }
                }

                override fun onCompleted(download: Download) {
                    val downloadEntity = DownloadEntity.fromDownload(download)
                    val index = downloads.indexOfFirst { it.id == download.id.toLong() }
                    if (index != -1) {
                        downloads[index] = downloadEntity
                    }
                    db?.let { db ->
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                val downloadDao = db.downloadDao()
                                downloadDao.update(downloadEntity)
                            }
                        }
                    }
                }

                override fun onDeleted(download: Download) {
                    val x = downloads.removeIf { it.id == download.id.toLong() }
                    if (x) {
                        db?.let { db ->
                            viewModelScope.launch {
                                withContext(Dispatchers.IO) {
                                    val downloadDao = db.downloadDao()
                                    downloadDao.deleteFromId(download.id.toLong())
                                }
                            }
                        }
                    }
                }

                override fun onDownloadBlockUpdated(
                    download: Download,
                    downloadBlock: DownloadBlock,
                    totalBlocks: Int
                ) {
                    val downloadEntity = DownloadEntity.fromDownload(download)
                    db?.let { db ->
                        val downloadDao = db.downloadDao()
                        downloadDao.update(downloadEntity)
                    }
                    val blockIndex = blocks.indexOfFirst {
                        it.downloadId == downloadBlock.downloadId && it.blockPosition == downloadBlock.blockPosition
                    }
                    if (blockIndex != -1) {
                        blocks[blockIndex] = downloadBlock
                    }

                }

                override fun onError(download: Download, error: Error, throwable: Throwable?) {
                    val downloadEntity = DownloadEntity.fromDownload(download)
                    downloadEntity.error = true
                    val index = downloads.indexOfFirst { it.id == download.id.toLong() }
                    if (index != -1) {
                        downloads[index] = downloadEntity
                    }
                    db?.let { db ->
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                val downloadDao = db.downloadDao()
                                downloadDao.update(downloadEntity)
                            }
                        }
                    }
                }

                override fun onPaused(download: Download) {
                    val downloadEntity = DownloadEntity.fromDownload(download)
                    downloadEntity.paused = true
                    val index = downloads.indexOfFirst { it.id == download.id.toLong() }
                    if (index != -1) {
                        downloads[index] = downloadEntity
                    }
                    db?.let { db ->
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                val downloadDao = db.downloadDao()
                                downloadDao.update(downloadEntity)
                            }
                        }
                    }
                }

                override fun onProgress(
                    download: Download,
                    etaInMilliSeconds: Long,
                    downloadedBytesPerSecond: Long
                ) {
                    val downloadEntity = DownloadEntity.fromDownload(download)
                    val index = downloads.indexOfFirst { it.id == download.id.toLong() }
                    if (index != -1) {
                        downloads[index] = downloadEntity
                    }
                    db?.let { db ->
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                val downloadDao = db.downloadDao()
                                downloadDao.update(downloadEntity)
                            }
                        }
                    }
                }

                override fun onQueued(download: Download, waitingOnNetwork: Boolean) {
                    val downloadEntity = DownloadEntity.fromDownload(download)
                    downloadEntity.waiting = waitingOnNetwork
                    db?.let { db ->
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                val downloadDao = db.downloadDao()
                                downloadDao.upsert(downloadEntity)
                            }
                        }
                    }
                }

                override fun onRemoved(download: Download) {
                    val x = downloads.removeIf { it.id == download.id.toLong() }
                    if (x) {
                        db?.let { db ->
                            viewModelScope.launch {
                                withContext(Dispatchers.IO) {
                                    val downloadDao = db.downloadDao()
                                    downloadDao.deleteFromId(download.id.toLong())
                                }
                            }
                        }
                    }
                }

                override fun onResumed(download: Download) {
                    val downloadEntity = DownloadEntity.fromDownload(download)
                    downloadEntity.paused = false
                    val index = downloads.indexOfFirst { it.id == download.id.toLong() }
                    if (index != -1) {
                        downloads[index] = downloadEntity
                    }
                    db?.let { db ->
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                val downloadDao = db.downloadDao()
                                downloadDao.update(downloadEntity)
                            }
                        }
                    }
                }

                override fun onStarted(
                    download: Download,
                    downloadBlocks: List<DownloadBlock>,
                    totalBlocks: Int
                ) {
                    val downloadEntity = DownloadEntity.fromDownload(download)
                    db?.let { db ->
                        val downloadDao = db.downloadDao()
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                val x = downloadDao.getFromId(download.id)
                                if (x != null) {
                                    downloadDao.update(downloadEntity)
                                } else {
                                    downloadDao.insert(downloadEntity)
                                }
                            }
                        }
                    }
                    blocks.addAll(downloadBlocks)
                }

                override fun onWaitingNetwork(download: Download) {
                    val downloadEntity = DownloadEntity.fromDownload(download)
                    downloadEntity.waiting = true
                    val index = downloads.indexOfFirst { it.id == download.id.toLong() }
                    if (index != -1) {
                        downloads[index] = downloadEntity
                    }
                    db?.let { db ->
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                val downloadDao = db.downloadDao()
                                downloadDao.update(downloadEntity)
                            }
                        }
                    }
                }
            })
        }
    }
}

val LocalDownloadModel = compositionLocalOf { DownloadViewModel() }