FROM  postgres:14.1

ADD ./scripts/initdb.sql /docker-entrypoint-initdb.d/
ENTRYPOINT ["docker-entrypoint.sh"]
CMD ["postgres"]
