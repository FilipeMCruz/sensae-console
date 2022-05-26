FROM  postgres:14.3

ADD ./scripts/*.sql /docker-entrypoint-initdb.d/
ENTRYPOINT ["docker-entrypoint.sh"]
CMD ["postgres"]
