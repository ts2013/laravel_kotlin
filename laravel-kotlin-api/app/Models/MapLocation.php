<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class MapLocation extends Model
{
    use HasFactory;
    protected $fillable = [
        'address',
        'city',
        'state',
        'zipcode',
        'latitude',
        'longitude',
        'description',

    ];
}
