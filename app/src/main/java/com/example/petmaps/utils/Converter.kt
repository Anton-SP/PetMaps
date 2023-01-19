package com.example.petmaps.utils

import com.example.petmaps.data.Mark
import com.example.petmaps.data.db.MarkEntity

fun MarkEntity.toMark() = Mark(id,coordinates,note)